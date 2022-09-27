// 전체 목록 가져오기
recentList();

// 최신순 클릭시
function recentList() {
    const url=`http://localhost:9089/api/zonning/recentList`;
    fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        }).then(res=>res.json())
        .then(res=>{
        console.log(res);
          if(res.header.rtcd == '00'){
            const result =
              res.data.map(product=>{
                    product.deadlineTime = product.deadlineTime.substr(10,6);
                  return ` <div class="item">
                                <a href="/products/${product.pnumber}" class="item__image"><img src="https://cdn.imweb.me/thumbnail/20210628/f9482d3bbe416.jpg"  alt=""></a>
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
            console.log(result.join(''));
             document.querySelector('.main .items').innerHTML=result.join('');
             if(!document.querySelector('.view_array-type').classList.contains('checked')){
                document.querySelector('.view_array-type').classList.add('checked');
             }
          }else{

          }
    }).catch(err=>console.log(err));
}

// 높은 할인순 클릭시
function discountListDesc() {
        const url=`http://localhost:9089/api/zonning/discountListDesc`;
    fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res=>res.json())
    .then(res=>{
    console.log(res);
      if(res.header.rtcd == '00'){
        const result =
          res.data.map(product=>{
              product.deadlineTime = product.deadlineTime.substr(10,6);
              return ` <div class="item">
                            <a href="/products/${product.pnumber}" class="item__image"><img src="https://cdn.imweb.me/thumbnail/20210628/f9482d3bbe416.jpg"  alt=""></a>
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
        console.log(result.join(''));
         document.querySelector('.main .items').innerHTML=result.join('');
      }else{

      }
    }).catch(err=>console.log(err));
}

// 낮은 가격순 클릭시
function priceList() {
    const url=`http://localhost:9089/api/zonning/priceList`;
    fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res=>res.json())
    .then(res=>{
    console.log(res);
      if(res.header.rtcd == '00'){
        const result =
          res.data.map(product=>{
                product.deadlineTime = product.deadlineTime.substr(10,6);
              return ` <div class="item">
                            <a href="/products/${product.pnumber}" class="item__image"><img src="https://cdn.imweb.me/thumbnail/20210628/f9482d3bbe416.jpg"  alt=""></a>
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
        console.log(result.join(''));
         document.querySelector('.main .items').innerHTML=result.join('');
      }else{

      }
    }).catch(err=>console.log(err));
}

// 높은 가격순 클릭시
function priceListDesc() {
    const url=`http://localhost:9089/api/zonning/priceListDesc`;
    fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res=>res.json())
    .then(res=>{
    console.log(res);
      if(res.header.rtcd == '00'){
        const result =
          res.data.map(product=>{
                product.deadlineTime = product.deadlineTime.substr(10,6);
              return ` <div class="item">
                            <a href="/products/${product.pnumber}" class="item__image"><img src="https://cdn.imweb.me/thumbnail/20210628/f9482d3bbe416.jpg"  alt=""></a>
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
        console.log(result.join(''));
         document.querySelector('.main .items').innerHTML=result.join('');
      }else{

      }
    }).catch(err=>console.log(err));
}