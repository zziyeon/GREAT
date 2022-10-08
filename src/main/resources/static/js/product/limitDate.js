const dateElement = document.getElementById('currentTime');
const date = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, -8);
console.log(date);
const date1 = new Date(new Date().getTime()).toISOString().slice(0, -8);
console.log(date1);
//dateElement.value = date;
//date=date.substr(0,20);
dateElement.setAttribute("min", date);
dateElement.setAttribute("max", date1);