// In your Javascript (external .js resource or <script> tag)
$(document).ready(function() {
    const $ingredientModal = $('#ingredientModal');
    const $ingredientAmount = $('#ingredientAmount');
    const $measurementUnit = $('#measurementUnit');
    const $ingredientsList = $('#ingredientsList');
    const $addIngredientBtn = $('#addIngredientBtn');
    $('#ingredientSelect').select2({
        theme: "bootstrap-5",
        dropdownParent: $ingredientModal,
        placeholder: 'Wyszukaj składnik',
        language: "pl",
        ajax: {
            url: '/api/ingredients/findIngredients',
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    textToSearch: params.term,
                    size: '10'
                };
            },
            processResults: function (data) {
                const mappedResults = data.content.map(item => ({
                    id: item.name,
                    text: item.name,
                    ...item
                }));
                return {
                    results: mappedResults
                };

            }
        }
    }).on('select2:select', function (e) {
        calculateIngredientInfo();
    });
    $measurementUnit.select2({
        theme: "bootstrap-5"
    });
    $ingredientAmount.on('input', function () {
        calculateIngredientInfo();
    });
    $ingredientModal.on('hidden.bs.modal', function () {
        resetIngredientModal();
    });
    $addIngredientBtn.on('click', function () {
        addIngredientToRecipe();
    });
    $ingredientsList.on('click', '.delete-ingredient-btn', function () {
        deleteIngredient($(this).closest('li'));
    });
    $ingredientsList.on('click', '.edit-ingredient-btn', function () {
        editIngredient($(this).closest('li'));
    });
    updateNutritionSummary();
});

function calculateIngredientInfo(){
    const $ingredientCardElement = $('#ingredientCard');
    let ingredientData = $('#ingredientSelect').select2('data');
    const ingredientAmount = parseFloat($('#ingredientAmount').val());
    if(ingredientData && ingredientAmount && $ingredientCardElement){
        if($(ingredientData[0].element).data('ingredientData')){
            ingredientData = $(ingredientData[0].element).data('ingredientData') ;
        }
        const caloriesAmount = parseFloat(ingredientData[0].caloriesAmount); // Wartość kalorii dla 100g/ml
        const proteinAmount = parseFloat(ingredientData[0].proteinAmount); // Wartość białka dla 100g/ml
        const fatsAmount = parseFloat(ingredientData[0].fatsAmount); // Wartość tłuszczy dla 100g/ml
        const carbsAmount = parseFloat(ingredientData[0].carbsAmount); // Wartość węglowodanów dla 100g/ml
        const glycemicIndex = parseFloat(ingredientData[0].glycemicIndex); // Wartość indeksu glikemicznego

        // Obliczenia i zaokrąglenie do dwóch miejsc po przecinku
        $('#calculatedCalories strong').text(((ingredientAmount / 100) * caloriesAmount).toFixed(2));
        $('#calculatedProtein strong').text(((ingredientAmount / 100) * proteinAmount).toFixed(2));
        $('#calculatedFat strong').text(((ingredientAmount / 100) * fatsAmount).toFixed(2));
        $('#calculatedCarbs strong').text(((ingredientAmount / 100) * carbsAmount).toFixed(2));
        $('#calculatedGlycemicIndex strong').text(glycemicIndex);

        if(!$ingredientCardElement.is(':visible')){
            $ingredientCardElement.removeClass('d-none');
        }
    }
}

function resetIngredientModal(){
    $('#calculatedCalories strong').text('');
    $('#calculatedProtein strong').text('');
    $('#calculatedFat strong').text('');
    $('#calculatedCarbs strong').text('');
    $('#calculatedGlycemicIndex strong').text('');
    $('#ingredientCard').addClass('d-none');
    $('#ingredientAmount').val('') ;
    $('#ingredientSelect').val(null).trigger('change');
    $('#ingredientSelect').prop('disabled', false);
}

function addIngredientToRecipe() {
    const $ingredientForm = $('#ingredientForm');
    if (!$ingredientForm[0].checkValidity()) {
        $ingredientForm[0].classList.add('was-validated')
        return;
    }

    const $ingredientCardElement = $('#ingredientCard');
    const ingredientData = $('#ingredientSelect').select2('data');
    const ingredientAmount = parseFloat($('#ingredientAmount').val());
    const measurementUnit = $('#measurementUnit').val();
    if (ingredientData && ingredientAmount && $ingredientCardElement) {
        const recipeIngredient = {
            name: ingredientData[0].text,
            amount: ingredientAmount,
            measurementUnit: measurementUnit
        };
        const $li = $(`
                        <li class="list-group-item d-flex align-items-center justify-content-between">
                          <div>
                            <span class="badge text-bg-success rounded-pill me-2">
                              <span class="ingredient-amount">${recipeIngredient.amount}</span> <span class="ingredient-measurement-unit">${recipeIngredient.measurementUnit}</span>
                            </span>
                            <span class="ingredient-name" 
                                    data-caloriesAmount="${ingredientData[0].caloriesAmount}"
                                    data-carbsAmount="${ingredientData[0].carbsAmount}"
                                    data-fatsAmount="${ingredientData[0].fatsAmount}"
                                    data-proteinAmount="${ingredientData[0].proteinAmount}"
                                    data-glycemicIndex="${ingredientData[0].glycemicIndex}">${recipeIngredient.name}</span>
                          </div>
                          <div>
                            <button type="button" class="btn btn-success btn-sm edit-ingredient-btn">
                              <i class="fa fa-pen" aria-hidden="true"></i>
                            </button>
                            <button type="button" class="btn btn-danger btn-sm delete-ingredient-btn">
                              <i class="fa fa-trash" aria-hidden="true"></i>
                            </button>
                          </div>
                        </li>
                      `);

        const $existingLi = $('#ingredientsList .ingredient-name').filter(function () {
            return $(this).text().trim() === recipeIngredient.name;
        }).closest('li');

        if ($existingLi.length > 0) {
            $existingLi.replaceWith($li);
        } else {
            $('#ingredientsList').append($li);
        }
        updateHiddenIngredients();
        updateNutritionSummary();
    }
    $('#ingredientModal').modal('hide');
}

function deleteIngredient($ingredientElement){
    $ingredientElement.remove();
    updateHiddenIngredients();
    updateNutritionSummary();
}

function editIngredient($ingredientElement){
    const name = $ingredientElement.find('.ingredient-name').text().trim();
    const amount = $ingredientElement.find('.ingredient-amount').text().trim();
    const measurementUnit = $ingredientElement.find('.ingredient-measurement-unit').text().trim();

    $.ajax({
        url: '/api/ingredients/findIngredientByName?name=' + name,
        success: function (data) {
            if (data) {
                const newOption = new Option(data.name, data.id, true, true);
                const ingredientData = [{
                    id: data.id,
                    text: data.name,
                    caloriesAmount: data.caloriesAmount,
                    proteinAmount: data.proteinAmount,
                    fatsAmount: data.fatsAmount,
                    carbsAmount: data.carbsAmount,
                    glycemicIndex: data.glycemicIndex
                }];

                // Dodajemy nową opcję do Select2
                $('#ingredientSelect').append(newOption).trigger('change');
                $(newOption).data('ingredientData', ingredientData);
            }
        }
    });
    $('#ingredientAmount').val(amount);
    $('#ingredientSelect').prop('disabled', true);
    $('#measurementUnit').val(measurementUnit).trigger('change');
    $('#ingredientModal').modal('show');
}

function updateHiddenIngredients() {
    const $container = $('#hiddenIngredientsContainer');
    $container.empty();
    $('#ingredientsList li').each(function(index) {
        const name = $(this).find('.ingredient-name').text().trim();
        const amount = $(this).find('.ingredient-amount').text().trim();
        const unit = $(this).find('.ingredient-measurement-unit').text().trim();

        $container.append(`<input type="hidden" name="ingredientsList[${index}].ingredientName" value="${name}" />`);
        $container.append(`<input type="hidden" name="ingredientsList[${index}].ingredientAmount" value="${amount}" />`);
        $container.append(`<input type="hidden" name="ingredientsList[${index}].ingredientMeasurementUnit" value="${unit}" />`);
    });
}


function updateNutritionSummary() {
    let totalCalories = 0;
    let totalProtein = 0;
    let totalFats = 0;
    let totalCarbs = 0;
    let weightedGI = 0;
    let totalWeight = 0;

    $('#ingredientsList li').each(function () {
        const $li = $(this);
        const $ingredientElement = $li.find('.ingredient-name');
        const amount = parseFloat($li.find('.ingredient-amount').text().trim()) || 0;
        const unit = $li.find('.ingredient-measurement-unit').text().trim();

        const caloriesAmount = parseFloat($ingredientElement.data('caloriesamount'));
        const carbsAmount = parseFloat($ingredientElement.data('carbsamount'));
        const fatsAmount = parseFloat($ingredientElement.data('fatsamount'));
        const proteinAmount = parseFloat($ingredientElement.data('proteinamount'));
        const glycemicIndex = parseFloat($ingredientElement.data('glycemicindex'));

        const factor = unit === 'g' || unit === 'ml' ? (amount / 100.0) : 1;

        totalCalories += (caloriesAmount || 0) * factor;
        totalProtein += (proteinAmount || 0) * factor;
        totalFats += (fatsAmount || 0) * factor;
        totalCarbs += (carbsAmount || 0) * factor;

        weightedGI += (glycemicIndex || 0) * amount;
        totalWeight += amount;
    });

    $('#caloriesAmount').val(totalCalories.toFixed(1));
    $('#proteinAmount').val(totalProtein.toFixed(1));
    $('#fatsAmount').val(totalFats.toFixed(1));
    $('#carbsAmount').val(totalCarbs.toFixed(1));

    let glycemicLoadText = 'n/a';
    let glycemicLoadClass = 'form-control';

    if (totalWeight > 0) {
        const avgGI = weightedGI / totalWeight;
        if (avgGI < 55) {
            glycemicLoadText = 'Niski';
            glycemicLoadClass += ' text-white bg-success';
        } else if (avgGI < 70) {
            glycemicLoadText = 'Średni';
            glycemicLoadClass += ' text-dark bg-warning';
        } else {
            glycemicLoadText = 'Wysoki';
            glycemicLoadClass += ' text-white bg-danger';
        }
    }

    $('#glycemicLoad').val(glycemicLoadText).attr('class', glycemicLoadClass);
}
