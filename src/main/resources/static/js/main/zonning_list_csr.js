// 전체 목록 가져오기
arrayType('recentList');

function arrayType(requestUrl) {
    const zone = document.querySelector(".title.content").dataset.zone;
    const urls = [
        `http://localhost:9089/api/zonning/recentList`,
        `http://localhost:9089/api/zonning/discountListDesc`,
        `http://localhost:9089/api/zonning/priceList`,
        `http://localhost:9089/api/zonning/priceListDesc`,
        `http://localhost:9089/api/zonning/kFood`
    ];
    const urlsWithZone = urls.map(url=>url+`?zone=${zone}`);

    const URL = requestUrl === 'recentList' ? urlsWithZone[0]
                : requestUrl === 'discountListDesc' ? urlsWithZone[1]
                : requestUrl === 'priceList' ? urlsWithZone[2]
                : requestUrl === 'priceListDesc' ? urlsWithZone[3]
                : requestUrl === 'kFood' ? urlsWithZone[4]
                : `doesn\'t exist`;

    if(URL === `doesn\'t exist`) {
        console.log("URL load error");
        // need error page append!
        return;
    }

    fetch(URL, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        }).then(res=>res.json())
              .then(res=>{
                let img_url = "";
                if(res.header.rtcd == '00'){
                  const result =
                    res.data.map(product=>{
                          product.deadlineTime = product.deadlineTime.substr(10,6);
                          if(product.imageFiles != null && product.imageFiles.length > 0) {
                            img_url = `<img class="good_Img" src="/api/attach/img/${product.imageFiles[0].code}/${product.imageFiles[0].storeFilename}" alt="이미지를 불러올수 없습니다">`;
                          } else {
                            img_url = `<img src="/img/product/등록된 사진이 없습니다.png" alt="">`;
                          }
                        return ` <div class="item">
                                      <a href="/products/${product.pnumber}" class="item__image">
                                        ${img_url}
                                      </a>
                                      <button class="heart_Btn"><i class="heartIcon far fa-heart fas"></i></button>
                                      <a href="#" class="item__title">${product.pname}</a>
                                      <div class="item__price-box">
                                          <div class="item__discount-rate">${product.discountRate}%</div>
                                          <div class="item__price">${product.salePrice}원</div>
                                          <div class="item__price--normal">${product.normalPrice}원</div>
                                      </div>
                                      <div class="item__time-box">
                                          <div class="item__magam">마감시간</div>
                                          <div class="item__time">${product.deadlineTime}</div>
                                      </div>
                                  </div>`;
                     });
                   document.querySelector('.main .items').innerHTML=result.join('');
                }else{

                }
              }).catch(err=>console.log(err));
}