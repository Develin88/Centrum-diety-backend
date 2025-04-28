const IngredientsTable = (() => {
    const fetchIngredientsData = params => {
        const url = '/api/ingredients/findIngredients';
        const requestParams = {
            size: params.data.limit,
            sortBy: params.data.sort,
            order: params.data.order,
            page: Math.floor(params.data.offset / params.data.limit),
            textToSearch: params.data.search
        };

        $.get(`${url}?${$.param(requestParams)}`)
            .then(res => {
                const transformedResponse = {
                    total: res.totalElements,
                    totalNotFiltered: res.totalElements,
                    rows: res.content
                };
                params.success(transformedResponse);
            })
            .fail(() => {
                params.error?.();
            });
    };

    const init = () => {
        const $addIngredientButton = $('#add-ingredient-btn');

        $addIngredientButton.click(function () {
            alert('test');
        });

    };

    return {
        init,
        fetchIngredientsData
    };
})();


IngredientsTable.init();
window.fetchIngredientsData = IngredientsTable.fetchIngredientsData;
