function plot() {
    let ctx = document.getElementById("canvas").getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.beginPath();
    ctx.fillStyle = "#7ca1ff";
    ctx.rect(110, 110, -80, 40);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(110, 110);
    ctx.arc(110, 110, 40, 0, Math.PI / 2, false);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(110, 100);
    ctx.lineTo(110, 70);
    ctx.lineTo(30, 110);
    ctx.lineTo(110, 110);
    ctx.closePath();
    ctx.fill();

    // axis
    ctx.beginPath();
    ctx.moveTo(110, 0);
    ctx.lineTo(110, 230);
    ctx.moveTo(0, 110);
    ctx.lineTo(230, 110);

    // arrows on axis
    ctx.moveTo(110, 0);
    ctx.lineTo(113, 5);
    ctx.moveTo(110, 0);
    ctx.lineTo(107, 5);

    ctx.moveTo(230, 110);
    ctx.lineTo(225, 113);
    ctx.moveTo(230, 110);
    ctx.lineTo(225, 107);


    // x lines
    ctx.fillStyle = "#121164";
    ctx.moveTo(30, 115);
    ctx.lineTo(30, 105);
    ctx.fillText("-R", 26, 125);

    ctx.moveTo(70, 115);
    ctx.lineTo(70, 105);
    ctx.fillText("-R/2", 60, 125);

    ctx.moveTo(150, 115);
    ctx.lineTo(150, 105);
    ctx.fillText("R/2", 144, 125);

    ctx.moveTo(190, 115);
    ctx.lineTo(190, 105);
    ctx.fillText("R", 186, 125);

    // y lines
    ctx.moveTo(115, 150);
    ctx.lineTo(105, 150);
    ctx.fillText("-R/2", 117, 153);

    ctx.moveTo(115, 190);
    ctx.lineTo(105, 190);
    ctx.fillText("-R", 117, 193);

    ctx.moveTo(115, 70);
    ctx.lineTo(105, 70);
    ctx.fillText("R/2", 117, 73);

    ctx.moveTo(115, 30);
    ctx.lineTo(105, 30);
    ctx.fillText("R", 117, 33);

    ctx.fillText("y", 115, 6);
    ctx.fillText("x", 224, 120);
    ctx.stroke();
}

function plotW(r) {
    let ctx = document.getElementById("canvas").getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.beginPath();
    ctx.fillStyle = "#7ca1ff";
    ctx.rect(110, 110, -80, 40);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(110, 110);
    ctx.arc(110, 110, 40, 0, Math.PI / 2, false);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(110, 100);
    ctx.lineTo(110, 70);
    ctx.lineTo(30, 110);
    ctx.lineTo(110, 110);
    ctx.closePath();
    ctx.fill();

    // axis
    ctx.beginPath();
    ctx.moveTo(110, 0);
    ctx.lineTo(110, 230);
    ctx.moveTo(0, 110);
    ctx.lineTo(230, 110);

    // arrows on axis
    ctx.moveTo(110, 0);
    ctx.lineTo(113, 5);
    ctx.moveTo(110, 0);
    ctx.lineTo(107, 5);

    ctx.moveTo(230, 110);
    ctx.lineTo(225, 113);
    ctx.moveTo(230, 110);
    ctx.lineTo(225, 107);


    // x lines
    ctx.fillStyle = "#121164";
    ctx.moveTo(30, 115);
    ctx.lineTo(30, 105);
    ctx.fillText("-" + r, 26, 125);

    ctx.moveTo(70, 115);
    ctx.lineTo(70, 105);
    ctx.fillText("-" + r / 2, 60, 125);

    ctx.moveTo(150, 115);
    ctx.lineTo(150, 105);
    ctx.fillText(r / 2, 144, 125);

    ctx.moveTo(190, 115);
    ctx.lineTo(190, 105);
    ctx.fillText(r, 186, 125);

    // y lines
    ctx.moveTo(115, 150);
    ctx.lineTo(105, 150);
    ctx.fillText("-" + r / 2, 117, 153);

    ctx.moveTo(115, 190);
    ctx.lineTo(105, 190);
    ctx.fillText("-" + r, 117, 193);

    ctx.moveTo(115, 70);
    ctx.lineTo(105, 70);
    ctx.fillText(r / 2, 117, 73);

    ctx.moveTo(115, 30);
    ctx.lineTo(105, 30);
    ctx.fillText(r, 117, 33);

    ctx.fillText("y", 115, 6);
    ctx.fillText("x", 224, 120);
    ctx.stroke();
}


function setPoint(event) {
    let canvas = document.getElementById("canvas");
    let rect = canvas.getBoundingClientRect();
    let offset = (rect.width - canvas.width) / 2 + 1;
    let x = event.clientX - rect.left - offset;
    let y = event.clientY - rect.top - offset;
    let r = parseFloat($('.rValue').val());

    x = (x - 108) / 80 * r;
    y = (108 - y) / 80 * r;
    if (
        (x >= -r && x <= 0 && y >= -r / 2 && y <= 0) ||
        (x >= 0 && y <= 0 && (x * x + y * y) <= (r / 2 * r / 2)) ||
        (x <= 0 && y >= 0 && y <= x / 2 + r / 2)
    ) {
        drawDotInside(x, y, r);
    } else {
        drawDotOutside(x, y, r);
    }
}

function applyHiddenValues(event) {
    let canvas = document.getElementById("canvas");
    let rect = canvas.getBoundingClientRect();
    let offset = (rect.width - canvas.width) / 2 + 1;
    let x = event.clientX - rect.left - offset;
    let y = event.clientY - rect.top - offset;
    let r = $('.rValue').val();

    x = (x - 108) / 80 * r;
    y = (108 - y) / 80 * r;
    x = x.toFixed(3);
    y = y.toFixed(3);
    $('.xCnv').val(x);
    $('.yCnv').val(y);
    $('.rCnv').val(r);
}

function showCoords(event) {
    let canvas = document.getElementById("canvas");
    let rect = canvas.getBoundingClientRect();
    let offset = (rect.width - canvas.width) / 2 + 1;
    let x = event.clientX - rect.left - offset;
    let y = event.clientY - rect.top - offset;
    let r = parseFloat($('.rValue').val());
    x = (x - 108) / 80 * r;
    y = (108 - y) / 80 * r;
    if (r === 0) {
        document.getElementById("err").style.animation = "appearing .5s";
        document.getElementById("err").style.opacity = 1;
        document.getElementById("err").innerHTML = "You should define R";
        $('#canvas').attr('disabled', 'disabled');
    } else {
        document.getElementById("check").style.animation = "appearing .5s";
        document.getElementById("check").style.opacity = 1;
        $('#canvas').removeAttr('disabled');
        document.getElementById("check").innerHTML = "X: " + x.toFixed(2) + " | Y: " + y.toFixed(2) + " | R: " + r;
    }
}

function eraseCoords() {
    let r = parseInt($('.rValue').val());
    if (r === 0) {
        document.getElementById("err").style.animation = "fadeInDisappearing .7s";
        document.getElementById("err").style.opacity = 0;

    } else {
        document.getElementById("check").style.animation = "fadeInDisappearing .7s";
        document.getElementById("check").style.opacity = 0;
    }
}

function drawDotInside(x, y, r) {
    let ctx = document.getElementById("canvas").getContext("2d");
    ctx.beginPath();
    ctx.rect(Math.round((108 + (x / r) * 80)), Math.round((108 - (y / r) * 80)), 4, 4);
    ctx.fillStyle = "#13e158";
    ctx.closePath();
    ctx.fill();
}

function drawDotOutside(x, y, r) {
    let ctx = document.getElementById("canvas").getContext("2d");
    ctx.beginPath();
    ctx.rect(Math.round((108 + (x / r) * 80)), Math.round((108 - (y / r) * 80)), 4, 4);
    ctx.fillStyle = "#e11751";
    ctx.closePath();
    ctx.fill();
}

function drawData(x, y, r) {
    if (
        (x >= -r && x <= 0 && y >= -r / 2 && y <= 0) ||
        (x >= 0 && y <= 0 && (x * x + y * y) <= (r / 2 * r / 2)) ||
        (x <= 0 && y >= 0 && y <= x / 2 + r / 2)
    ) drawDotInside(x, y, r);
    else drawDotOutside(x, y, r);
}





