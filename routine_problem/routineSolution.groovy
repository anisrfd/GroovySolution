def products = [
    ["A", "G1", 20.1],
    ["B", "G2", 98.4],
    ["C", "G1", 49.7],
    ["D", "G3", 35.8],
    ["E", "G3", 105.5],
    ["F", "G1", 55.2],
    ["G", "G1", 12.7],
    ["H", "G3", 88.6],
    ["I", "G1", 5.2],
    ["J", "G2", 72.4]]
 
def category = [
    ["C3", 50, 75],
    ["C4", 75, 100],
    ["C2", 25, 50],
    ["C5", 100, null],
    ["C1", 0, 25]]
 
def margins = [
    "C1" : "20%",
    "C2" : "30%",
    "C3" : "0.4",
    "C4" : "50%",
    "C5" : "0.6"]
  
def result = [
    "G1" : 0.0,
    "G2" : 0.0,
    "G3" : 0.0];
    
def margin = "";
def count = [0,0,0];


products.inject(["", "", 0.0]) {key, val ->
for(product in category) {
    if (((val[2] >= product[1]) && (val[2] < product[2])) || ((val[2] >= product[1]) && (product[2] == null))) {
        margin = margins[product[0]];
        val[2] = val[2].multiply((1.plus((margin.indexOf('%') > -1) ? (margin.minus("%").toInteger() / 100) : margin.toFloat())));
        break;
    }
  }
}

products.each {
    switch(it[1]) {            
    /* There is case statement defined for 3 cases based on groups.
       Each case statement section has a break condition to exit the loop */
    case "G1": 
        result["G1"] = result["G1"].plus(it[2]);
        count[0] += 1; 
        break; 
    case "G2": 
        result["G2"] = result["G2"].plus(it[2]);
        count[1] += 1;
        break; 
    case "G3": 
        result["G3"] = result["G3"].plus(it[2]);
        count[2] += 1; 
        break; 
    default: 
        println("The product group is unknown"); 
        break; 
    }
}

/* The Price of each Product is calculated as:
   Cost * (1 + Margin) */
result.eachWithIndex{it, index ->
    it.value = (it.value / count[index]).round(1);
}


assert result == [
    "G1" : 37.5,
    "G2" : 124.5,
    "G3" : 116.1
    ] : "It doesn't work"
 
println "It works!"
