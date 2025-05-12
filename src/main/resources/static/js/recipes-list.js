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
        '<a href="/edit-recipe?name='+value.name+'" title="Edit Recipe">',
        '<i class="fa fa-pen"></i>',
        '</a>  ',
    ].join('')

    const init = () => {
        const $addRecipeButton = $('#add-recipe-btn');

        $addRecipeButton.click(function () {
            alert('test');
        });

    };

    return {
        init,
        fetchRecipesData,
        actionsFormatter
    };
})();


RecipesTable.init();
window.fetchRecipesData = RecipesTable.fetchRecipesData;
window.actionsFormatter = RecipesTable.actionsFormatter;
