// 검색 입력창
const $searchInput =document.querySelector('.bottom-menu .search .search__search-box');
// 검색 버튼
const $headerSearchBtn = document.querySelector('.bottom-menu .search i');

// 검색 버튼 클릭시
$headerSearchBtn.addEventListener('click', search_h);

function search_h(){
    console.log("클릭됨");
    // 검색어 입력 유무 체크
    if($searchInput.value.trim().length ===0){
        alert('검색어를 입력하세요');
        $searchInput.focus();
        $searchInput.select();
        return false;
    }
    const url = `/searchresult?searchKeyword=${$searchInput.value}`;
    location.href=url;
}


