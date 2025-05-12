const RecipesTable = (() => {
    const fetchRecipesData = params => {
        const url = '/api/recipes/findRecipes';
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

    const getSelectedDietitians = () => {
        const $table = $('#dietitians-table');
        return $table.bootstrapTable('getSelections').map(row => row.userName);
    };

    const actionsFormatter = (e, value, row) => [
        '<a href="/view-recipe?name='+value.name+'" title="View Recipe">',
        '<i class="fa fa-magnifying-glass"></i>',
        '</a>  ',
        '<a class="ms-2" href="/edit-recipe?name='+value.name+'" title="Edit Recipe">',
        '<i class="fa fa-pen"></i>',
        '</a>  ',
    ].join('')
    return {
        fetchRecipesData,
        actionsFormatter
    };
})();


window.fetchRecipesData = RecipesTable.fetchRecipesData;
window.actionsFormatter = RecipesTable.actionsFormatter;
