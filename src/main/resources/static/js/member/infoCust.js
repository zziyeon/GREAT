//모달팝업 열기
function show() {
    document.querySelector(".background").className = "background show";
}
//모달팝업 닫기
function close() {
    document.querySelector(".background").className = "background";
}


//닉네임 중복확인 버튼
const $dupChkNn = document.querySelector('.dup-chk-nn-btn');

//닉네임 중복확인 버튼 클릭시
$dupChkNn.addEventListener('click', e => {
    console.log('닉네임 중복확인 클릭!');
    const nnVal = memNickname.value;
    if (nnVal.length == 0) {
        alert('닉네임을 입력해주세요.');
        nnVal.focus();
        return;
    }
    dupChkNn(nnVal);
});

//닉네임 중복확인 함수
function dupChkNn(nnVal) {
    const url = `/api/member/dupChkNickname`;
    const data = { "memNickname" : nnVal };
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(data)
    }).then(res => res.json())
      .then(res => {
        console.log(res)
        if (res.header.rtcd == '00') {
            alert ('사용가능한 닉네임입니다!')
            $dupChkNn.classList.remove('bad')
            $dupChkNn.classList.add('good')
        } else {
            alert ('중복된 닉네임이 존재합니다.')
            $dupChkNn.classList.remove('good')
            $dupChkNn.classList.add('bad')
        }
      })
      .catch(err => console.log(err));
}

//인증코드 발송 버튼
$sendCodeBtn = document.querySelector('.send-code-btn');

//인증코드 발송 버튼 클릭시
$sendCodeBtn.addEventListener('click', e => {
    const mailVal = memEmail.value;
    if (mailVal == null) {alert('인증코드를 받을 이메일을 입력하세요.');}
    sendCode(mailVal);

    console.log(mailVal);
});

//인증코드 발송 함수
function sendCode(mailVal) {
    const url = `/api/member/mailConfirm`;
    const data = { "email" : mailVal };
      fetch(url, {
        method:'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
      }).then(res => res)
        .then(res => {
            console.log(res)
            console.log(res.body)
            console.log(res.headers)
            if (res.ok == true) {
                alert ('발송되었습니다! 메일을 확인해주세요.')
            } else {
                alert ('메일주소를 다시 확인해주세요.')
            }
        })
        .catch(err => console.log(err));
}

//인증코드 확인 버튼
$confirmCodeBtn = document.querySelector('.confirm-code-btn');

//인증코드 확인 버튼 클릭시
$confirmCodeBtn.addEventListener('click', e => {
    console.log('인증코드 확인 버튼 클릭');
    const mailVal = memEmail.value;
    const codeVal = memCode.value;

    confirmCode(mailVal, codeVal);
});

//인증코드 확인 함수
function confirmCode(mailVal, codeVal) {
    const url = `/api/member/codeConfirm`;
    const data = { "email" : mailVal,
                   "code" : codeVal };
    fetch(url, {
            method:'POST',
            headers: {
              'Content-type': 'application/json'
            },
            body: JSON.stringify(data)
          }).then(res => res.json())
            .then(res => {
                console.log(res)
                if (res.header.rtcd = '00') {
                    alert ('인증번호 확인되었습니다!')
                } else {
                    alert ('인증번호를 다시 확인해주세요.')
                }
            })
            .catch(err => console.log(err));
}


//회원탈퇴 버튼
const $exitBtn = document.querySelector('.exit-btn');

//회원탈퇴 버튼 클릭시
$exitBtn.addEventListener('click', e => {
    //회원탈퇴
    exit(memNumber);

    //회원탈퇴 완료 후 메인화면으로 이동
    window.location.href = 'http://localhost:8080/';
});