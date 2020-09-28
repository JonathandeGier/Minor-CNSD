// 5
console.log("Exercise 5 (if statement to expression)")
var number = Math.random();
var x_stmnt;
if (number > 0.7) {
    x_stmnt = 1;
} else {
    x_stmnt = 0;
}

var x_expr = number > 0.7 ? 1 : 0;

console.log(x_stmnt === x_expr);
console.log("")

// 6
console.log("Exercise 6 (random array with values above 33)")
var arr = Array(50).fill(null);

var i;
var arr2_stmnt = [];
for (i = 0; i < arr.length; i += 1) {
    var val = Math.random();
    if (val < 0.3) continue;
    val = Math.floor(val * 100);
    arr2_stmnt.push(val);
}
console.log(arr2_stmnt);

var arr2_expr = arr.map(value => Math.floor(Math.random() * 100)).filter(value => value > 33);
console.log(arr2_expr);
console.log("");

// 7
console.log("Exercise 7 (syntax)")
var foo = function foo(x) {
    if (x > 4) {
        throw new Error('x > 4');
    }

    return (x += 1) * 100;
}
var arr3 = [1, 2, 3, 3, 3].map(foo);
console.log(arr3);
console.log("");

// 8
console.log("Exercise 8 (predict outcome)")
console.log(5 == new Number(5));    // true V
console.log(new Number(5) == new Number(5));    // true X
console.log(5 === new Number(5));    // false V
console.log(new Number(5) === new Number(5));    // true X
console.log(5 == [5]);    // false X
console.log(5 === [5]);    // false V
console.log(0 == false);    // true V
console.log(1 == true);    // true V
console.log(2 == true);    // true X
console.log('' == false);    // true V
console.log('0' == false);    // false X
console.log('' ? true : false);    // false V
console.log('0' ? true : false);    // true V
console.log(false == null);    // false V
console.log(false == undefined);    // false V
console.log(null == undefined);    // true V
console.log("Result: 11/16");
console.log("");

// 9
function fibonacci_stmt(n) {
    if (n === 0) {
        return 0
    } else if (n ===1) {
        return 1
    } else {
        return fibonacci_stmt(n - 1) + fibonacci_stmt(n - 2);
    }
}

var fibonacci_func = function (n) {return n === 0 ? 0 : n === 1 ? 1 : fibonacci_func(n - 1) + fibonacci_func(n - 2);}

// 10
function teller(startwaarde) {
    return {
        startwaarde: startwaarde,
        metStapGrootte: function (stapGrootte) {
            return {
                waarde: this.startwaarde,
                stapGrootte: stapGrootte,
                volgende: function () {
                    this.waarde += this.stapGrootte;
                    return this.waarde;
                }
            }
        }
    }
}













