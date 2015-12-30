function doAjax(url, type, dataType, data, reloadPage)
{

    return $.ajax
    ({
        url: url,
        type: type,
        dataType: dataType,
        data: data,
        error: function (errorThrown)
        {
            console.log(errorThrown);
            showIfError({status: errorThrown.status, message: errorThrown.statusText });
        }
    });
}

function showIfError(response)
{
    $('#info-modal-text').text('Error! ' + response.message);
    $('#info-modal').modal('show');
}



function makeRowsInTable(listObjects, $tbody)
{

    var $lastTr = $tbody.children(0),
        buttonEdit = '<button type="button" data-toggle="modal" data-target="#update-modal" class="glyphicon glyphicon-edit btn-success update-row" aria-hidden="true"></button>',
        buttonDelete = '<button type="button" data-toggle="modal" data-target="#delete-modal" class="glyphicon glyphicon-remove btn-danger remove-row" aria-hidden="true"></button>';

    for(var i=0; i<listObjects.length; i++)
    {
        listObjects[i].buttonEdit = buttonEdit;
        listObjects[i].buttonDelete = buttonDelete;
        createRow(listObjects[i], $lastTr);
    }

}

function createRow(object, $lastTr)
{

   var $tr = $('<tr>').insertBefore($lastTr);
   for(key in object)
        createCol(object[key], $tr);

}

function createCol(value, $tr)
{
    $th = $('<th>').appendTo($tr);
    if(isObject(value))
    {
        $th.attr('data-value', value[Object.keys(value)[0]]);
        $th.append(createTextToOptionSelect(value));
    }
    else
    {
        $th.append(value);
    }

}

function createOptionSelect($select, listObjects)
{
    var $option,
        object;

    for(i in listObjects)
    {
        $option = $('<option>').appendTo($select);
        object = listObjects[i];
        $option.text(createTextToOptionSelect(listObjects[i]));
        $option.attr('value', object[Object.keys(object)[0]]);
    }


}

function createTextToOptionSelect(object)
{
    var text = "ID: " + object[Object.keys(object)[0]];

    if(object.hasOwnProperty('idAuthor') || object.hasOwnProperty('idReader'))
    {
        text += ", " + object.name + " " + object.surname;
    }
    else if(object.hasOwnProperty('idBook'))
    {
        text += ", " + object.title;
    }

    return text;
}



function isObject(val)
{
    if (val === null)
    {
        return false;
    }
    return ( (typeof val === 'function') || (typeof val === 'object') );
}

function isValidDate(dateString)
{
    // First check for the pattern
    var regex_date = /^\d{4}\-\d{1,2}\-\d{1,2}$/;

    if(!regex_date.test(dateString))
    {
        return false;
    }

    // Parse the date parts to integers
    var parts   = dateString.split("-");
    var day     = parseInt(parts[2], 10);
    var month   = parseInt(parts[1], 10);
    var year    = parseInt(parts[0], 10);

    // Check the ranges of month and year
    if(year < 1000 || year > 3000 || month == 0 || month > 12)
    {
        return false;
    }

    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

    // Adjust for leap years
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
    {
        monthLength[1] = 29;
    }

    // Check the range of the day
    return day > 0 && day <= monthLength[month - 1];
}