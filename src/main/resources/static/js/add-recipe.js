// In your Javascript (external .js resource or <script> tag)
$(document).ready(function() {
    $('#ingredientSelect').select2({
        theme: "bootstrap-5",
        dropdownParent: $('#ingredientModal'),
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
    $('#measurementUnit').select2({
        theme: "bootstrap-5"
    });
    $('#ingredientAmount').on('input', function () {
        calculateIngredientInfo();
    });
});

function calculateIngredientInfo(){
    const $ingredientCardElement = $('#ingredientCard');
    const ingredientData = $('#ingredientSelect').select2('data');
    const ingredientAmount = parseFloat($('#ingredientAmount').val());
    if(ingredientData && ingredientAmount && $ingredientCardElement){
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
