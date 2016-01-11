$(document).ready(function()
{
    var idAuthor,
        name,
        surname,
        $updateAlert = $('#update-alert'),
        $inputsUpdate = $('#inputs-update').children(),
        $tbody = $('#authors-tbody');

    doAjax('../rest/author/getAllAuthors', 'GET', 'JSON', '').success(
    function(response)
    {
        makeRowsInTable(response, $tbody);
    });

    $tbody.on('click', '.remove-row', function()
    {
        idAuthor = $(this).closest('tr').children().eq(0).text();
    });
    $("#delete-btn").on('click', function()
    {
        if(typeof null != idAuthor && typeof 'undefined' != idAuthor )
        {
            doAjax('../rest/author/deleteAuthor', 'DELETE', 'TEXT', {idAuthor: idAuthor}).success(function(response){ location.reload(true); });
        }
    });
    $tbody.on('click', '.update-row', function()
    {
        var $this = $(this).closest('tr').children();
        $updateAlert.removeClass('in');
        $updateAlert.text('');
        idAuthor = $this.eq(0).text();
        name = $this.eq(1).text();
        surname = $this.eq(2).text();
        $inputsUpdate.eq(0).val(idAuthor);
        $inputsUpdate.eq(1).val(name);
        $inputsUpdate.eq(2).val(surname);
    });
    $('#update-form').submit(function(e)
    {
        $updateAlert.removeClass('in');
        $updateAlert.text('');
        var newIdAuthor = $inputsUpdate.eq(0).val(),
            newName = $inputsUpdate.eq(1).val(),
            newSurname = $inputsUpdate.eq(2).val();
        if(idAuthor != '' && typeof idAuthor != 'undefined' && idAuthor == newIdAuthor)
        {
            if(newName == name && newSurname == surname)
            {
                $('#update-modal').modal('hide');
            }
            else if(newName.length < 2 || newName.length > 30 || newSurname.length < 2 || newSurname.length > 30)
            {
                 $updateAlert.text('Name and surname should have minimum 3 and maximum 30 characters!');
                 $updateAlert.addClass('in');
            }
            else
            {
                 doAjax('../rest/author/updateAuthor', 'PUT', 'JSON', {idAuthor:idAuthor, name: newName, surname: newSurname}).success(
                 function(response)
                 {
                     $('#update-modal').modal('hide');
                     location.reload(true);
                 });


            }
            e.preventDefault();
        }
    });
    $('#add-btn').click(function()
    {
    	var $addAlert = $('#add-alert');
    	$addAlert.removeClass('in');
    	$addAlert.text('');
    	name = $('#name').val();
    	surname = $('#surname').val();
    	if(name.length < 2 || name.length > 30 || surname.length < 2 || surname.length > 30)
    	{
    		$addAlert.text('Name and surname should have minimum 3 and maximum 30 characters!');
    		$addAlert.addClass('in');
    	}
    	else
    	{
    		doAjax('../rest/author/addAuthor', 'POST', 'JSON', {name: name, surname: surname}).success(
            function(response)
            {
                location.reload(true);
            });;
    	}
    });
});