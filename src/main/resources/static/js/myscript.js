document.addEventListener('DOMContentLoaded', function () {
    var quantityInput = document.getElementById('quantity');
    var incrementBtn = document.getElementById('incrementBtn');
    var decrementBtn = document.getElementById('decrementBtn');

    if (quantityInput && incrementBtn && decrementBtn) {
        incrementBtn.addEventListener('click', function () {
            var currentQuantity = parseInt(quantityInput.value, 10);
            quantityInput.value = currentQuantity + 1;
        });

        decrementBtn.addEventListener('click', function () {
            var currentQuantity = parseInt(quantityInput.value, 10);
            if (currentQuantity > 1) {
                quantityInput.value = currentQuantity - 1;
            }
        });
    }
});

addToCart = function(productId, amount, cartId) {
    productId = parseInt(productId);
    amount = parseInt(amount);
    $.ajax({
        type: "POST",
        url: "/addToCart/add",
        data: {
            productId: productId,
            cartId: cartId,
            amount: amount
        },
        success: function (response) {
            alert(response);
        },
        error: function () {
            console.log(cartId)
            alert("Error adding product to cart");
        }
    });
}