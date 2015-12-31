$(document).ready(function()
{
    var idHiring,
        idBook,
        idReader,
        hireDate,
        $updateAlert = $('#update-alert'),
        $inputsUpdate = $('#inputs-update').children(),
        $tbody = $('#hiring-tbody');

    $( '.form-date' ).datetimepicker( { format: 'YYYY-MM-DD' } );

    doAjax('../rest/hiring/getAllHirings', 'GET', 'JSON', '').then(
    function(response)
    {
        makeRowsInTable(response, $tbody);
    });

    doAjax('../rest/book/getAllBooks',  'GET', 'JSON', '').then(
    function(response)
    {
        createOptionSelect($('#select-book-add'), response);
        createOptionSelect($inputsUpdate.eq(1), response);
    });

    doAjax('../rest/reader/getAllReaders',  'GET', 'JSON', '').then(
    function(response)
    {
        createOptionSelect($('#select-reader-add'), response);
        createOptionSelect($inputsUpdate.eq(2), response);
    });

    $tbody.on('click', '.remove-row', function()
    {
        idHiring = $(this).closest('tr').children().eq(0).text();
    });

    $("#delete-btn").on('click', function()
    {
        if(typeof idHiring != null && typeof idHiring != 'undefined' )
        {
            doAjax('../rest/hiring/deleteHiring', 'DELETE', '', {idHiring: idHiring})
                .success(function(response){ location.reload(true); });
        }
    });

    $tbody.on('click', '.update-row', function()
    {
       var $this = $(this).closest('tr').children();
       $updateAlert.removeClass('in');
       $updateAlert.text('');
       $('#inputs-update select').children().prop('selected', false);
       idHiring = $this.eq(0).text();
       idBook= $this.eq(1).attr('data-value');
       idReader = $this.eq(2).attr('data-value');
       hireDate = $this.eq(3).text();
       $inputsUpdate.eq(0).val(idHiring);
       $inputsUpdate.eq(1).find('option[value='+idBook+']').prop('selected', true);
       $inputsUpdate.eq(2).find('option[value='+idReader+']').prop('selected', true);
       $inputsUpdate.eq(3).val(hireDate);
    });

    $('#update-form').submit(function(e)
    {
        $updateAlert.removeClass('in');
        $updateAlert.text('');
        var newIdHiring = $inputsUpdate.eq(0).val(),
            newIdBook = $inputsUpdate.eq(1).val(),
            newIdReader = $inputsUpdate.eq(2).val(),
            newHireDate = $inputsUpdate.eq(3).val();
        if(idHiring != '' && typeof idHiring != 'undefined' && idHiring == newIdHiring)
        {
            if(newIdBook == idBook && idReader == newIdReader && hireDate == newHireDate)
            {
                $('#update-modal').modal('hide');
            }
            else if(!isValidDate(newHireDate))
            {
                $updateAlert.text('Hire date is incorrect (correct format is yyyy-mm-dd)');
                $updateAlert.addClass('in');
            }
            else
            {
                doAjax('../rest/hiring/updateHiring', 'PUT', '',
                {
                    idHiring: newIdHiring,
                    idBook: newIdBook,
                    idReader: newIdReader,
                    hireDate: newHireDate

                 }).success(function(response){ location.reload(true); });
                $('#update-modal').modal('hide');
            }
            e.preventDefault();
        }
    });

    $('#add-btn').click(function()
    {
    	var $addAlert = $('#add-alert');
    	$addAlert.removeClass('in');
    	$addAlert.text('');
    	var idBook= $('#select-book-add').val(),
            idReader = $('#select-reader-add').val(),
            hireDate = $('#hire-date').val();
    	if(idBook == '')
    	{
    		$addAlert.text('You have not selected book...');
    		$addAlert.addClass('in');
    	}
    	else if(idReader == '')
    	{
    		$addAlert.text('You have not selected reader...');
    		$addAlert.addClass('in');
    	}
    	else if(!isValidDate(hireDate))
    	{
    		$addAlert.text('Hire date is incorrect (correct format is yyyy-mm-dd)');
    		$addAlert.addClass('in');
    	}
    	else
    	{
    		doAjax('../rest/hiring/addHiring', 'POST', '', { idBook: idBook, idReader: idReader, hireDate: hireDate})
    		    .success(function(response){ location.reload(true); });
    	}
    });

});