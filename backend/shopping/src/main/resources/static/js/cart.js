function addItemToCart(itemId, quantity) {
    let items = JSON.parse(window.localStorage.getItem('items'));

    if (items === undefined || items === null) items = [];

    let item = items.find(item => item.id === itemId);

    if (item === undefined || item === null) {
        items.push({
            'id': itemId,
            'quantity': Number(quantity)
        });
    } else {
        item.quantity = Number(item.quantity) + Number(quantity);
    }

    window.localStorage.setItem('items', JSON.stringify(items));

}