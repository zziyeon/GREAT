var slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("banner");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) { slideIndex = 1 }
    if (n < 1) { slideIndex = slides.length }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex - 1].style.display = "block";
    dots[slideIndex - 1].className += " active";
    setTimeout(showSlides, 6000);
}

//document.querySelector('.heart_Btn').addEventListener('click', heart);

// mouse click event
function heart(e) {
    console.log('하트 클릭됨!');
    e.target.classList.toggle('active');
}

// 검색 입력창
const $searchInput =document.querySelector('.bottom-menu .search .search__search-box');
// 검색 버튼
const $searchBtn = document.querySelector('.bottom-menu .search i');

// 검색 버튼 클릭시
$searchBtn.addEventListener('click', search_h);

function search_h(){
    console.log("클릭됨");
    // 검색어 입력 유무 체크
    if($searchInput.value.trim().length ===0){
        alert('검색어를 입력하세요');
        $searchInput.focus();
        $searchInput.select();
        return false;
    }
    const url = `?search=${$searchInput.value}`;
    location.href=url;
}




