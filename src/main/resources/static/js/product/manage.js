// 날짜 선택
let $startDate = document.querySelector('#startDate');
let $finishDate= document.querySelector('#finishDate');

// 현재 접속 시간 -> Mon Oct 03 2022 14:17:44 GMT+0900 (한국 표준시)
let nowTime = new Date();
startDate=lastdateFormat(nowTime);
finishDate=nowdateFormat(nowTime);

$startDate.value=startDate;
$finishDate.value=finishDate;

// Date()를 오늘 YYYY-MM-DD로 변환
function nowdateFormat(date){
    let month = date.getMonth() +1;
    let day = date.getDate();

    month= month >=10 ? month : '0' + month;
    day= day>=10?day:'0'+day;

    return date.getFullYear()+'-'+month+'-'+day;
}
// Date()를 어제 YYYY-MM-DD로 변환
function lastdateFormat(date){
    let month = date.getMonth();
    let day = date.getDate();

    month= month >=10 ? month : '0' + month;
    day= day>=10?day:'0'+day;

    return date.getFullYear()+'-'+month+'-'+day;
}

// 날짜 조회 버튼
const $period = document.querySelector('.drop__period #searchBtn');

// 입력데이터 가져오기
function getInputData(){
    const startDateData = $startDate.value;
    const finishDateData = $finishDate.value;

    return {
        "startDate" : startDateData,
        "endDate" : finishDateData
    };
}

// 날짜 조회 버튼 클릭
$period .addEventListener('click', e=>{
    console.log("클릭됨");

    // 1) 입력데이터 가져오기
    const inputDate = getInputData();
    // 2) 날짜 조회 처리
    search(inputDate);
});

function search(searchData){
console.log('searchData.startDate -> ' + searchData.startDate);
console.log('searchData.endDate -> ' + searchData.endDate);

manageList(searchData.startDate, searchData.endDate);
//const url = 'http://localhost:9080/api/manage/9?history_start_date=' + searchData.startDate + '&history_end_date=' + searchData.endDate;
//    fetch(url, {
//        method:'GET',
//        headers:{
//            'Accept':'application/json'
//        },
//    }).then(res=>res.json())
//        .then(res=>{
//            console.log(res);
//            if(res.header.rtcd == '00'){
//            // 검색 결과
//            console.log("성공");
//            }
//        })
}

//-------------------------------------------
// 해당 점주 전체 상품 목록 조회
manageList($startDate.value, $finishDate.value);

// 상품관리 목록
function manageList(startDate, endDate) {
const url = 'http://localhost:9080/api/manage/9?history_start_date=' + startDate + '&history_end_date=' + endDate;
fetch(url, {
  methode: 'GET',
  headers:{
    'Accept':'application/json'
  }
}).then(res=>res.json())
  .then(res=>{
    if(res.header.rtcd == '00'){
      console.log(res.pName);
      let i =0;
      const result =
        res.data.map(product =>{
          i++;
          product.rdate=product.rdate.substr(0,10);
          if(product.imageFiles != null && product.imageFiles.length > 0) {
            img_url = `<img class="good_Img" src="/api/attach/img/${product.imageFiles[0].code}/${product.imageFiles[0].storeFilename}" alt="이미지를 불러올수 없습니다">`;
          } else {
            img_url = `<img src="/img/product/등록된 사진이 없습니다.png" alt="">`;
          }
          return `<tr>
                    <td>${i}</td>
                    <td>${img_url}</td>
                    <td>
                      <select name="" id="">
                        <option value="판매중">판매중</option>
                        <option value="삭제">삭제</option>
                        <option value="판매완료">판매완료</option>
                      </select>
                    </td>
                    <td>${product.pname}</td>
                    <td>${product.salePrice}/ ${product.normalPrice}원</td>
                    <td>${product.remainCount}/${product.totalCount}개</td>
                    <td>${product.rdate}</td>
                    <p></p>
                    <td><a class="updateBtn" href="/products/${product.pnumber}/edit">수정</a></td>
                  </tr>
          `;
        });
        document.querySelector('.product_manage-tb tbody').innerHTML=result.join('');
    }
  }).catch(err=>console.log(err));
}