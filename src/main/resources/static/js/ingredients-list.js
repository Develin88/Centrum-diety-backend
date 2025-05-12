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

    const actionsFormatter = (e, value, row) => [
        '<a href="/edit-ingredient?name='+value.name+'" title="Edit Ingredient">',
        '<i class="fa fa-pen"></i>',
        '</a>  ',
    ].join('')
    return {
        fetchIngredientsData,
        actionsFormatter
    };
})();


IngredientsTable.init();
window.fetchIngredientsData = IngredientsTable.fetchIngredientsData;
window.actionsFormatter = IngredientsTable.actionsFormatter;
