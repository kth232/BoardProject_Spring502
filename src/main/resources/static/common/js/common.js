const commonLib = {
    //공통 라이브러리
    /**
     * ajax 요청 공통 기능
     *
     * @Param responseType : 응답 데이터 타입(text - text로, 그외는 json)
     * @param url <-필수 데이터
     * @param method <-매개변수에 직접 기본값 정의 가능
     * @param data 전송 데이터
     * @param headers 헤더 정보
     */
    ajaxLoad(url, method = "GET", data, headers, responseType) {
        if (!url) {
            return;
        }
      //npe(NullPointerException)에 안정적으로 만들기
      //?. : 옵셔널 체이닝, null, undefined일 때 오류 방지를 위함, 무시하고 undefined로 대체해서 내보냄
        const csrfToken = document.querySelector("meta[name='csrf_token']")?.content?.trim();
        const csrfHeader = document.querySelector("meta[name='csrf_header']")?.content?.trim();

        if (!/^http[s]?/i.test(url)) {
            let rootUrl = document.querySelector("meta[name='rootUrl']")?.content?.trim() ?? '';
            rootUrl = rootUrl === '/' ? '' : rootUrl;

            url = location.protocol + "//" + location.host + rootUrl + url;
        }

        method = method.toUpperCase();
        if (method === 'GET') {
            data = null;//get 방식일 때는 바디 데이터 없음
      }

        if (data && !(data instanceof FormData) && typeof data !== 'string' && data instanceof Object) {
            data = JSON.stringify(data);
        }

        if (csrfHeader && csrfToken) {
            headers = headers ?? {};
            headers[csrfHeader] = csrfToken;
          //헤더를 보낼 때마다 토큰을 실어서 내보냄
      }
        const options = {
            method
        };

        if (data) options.body = data;
        if (headers) options.headers = headers;

        return new Promise((resolve, reject) => {
            fetch(url, options)
                .then(res => responseType === 'text' ? res.text() : res.json()) // res.json() - JSON / res.text() - 텍스트
                .then(data => resolve(data))
                .catch(err => reject(err));
        });
    },
    /**
     * 에디터 로드
     *
     */
    editorLoad(id) {
        if(!ClassicEditor || !id?.trim()) return;

        return ClassicEditor.create(document.getElementById(id.trim()), {});
    }
};

/**
 * 이메일 인증 메일 보내기
 *
 * @param email : 인증할 이메일
 */
commonLib.sendEmailVerify = function(email) {
    const { ajaxLoad } = commonLib;

    const url = `/email/verify?email=${email}`;

    ajaxLoad(url, "GET", null, null, "json")
        .then(data => {
            if (typeof callbackEmailVerify == 'function') { // 이메일 승인 코드 메일 전송 완료 후 처리 콜백
                callbackEmailVerify(data);
            }
        })
        .catch(err => console.error(err));
};

/**
 * 인증 메일 코드 검증 처리
 *
 */
commonLib.sendEmailVerifyCheck = function(authNum) {
    const { ajaxLoad } = commonLib;
    const url = `/email/auth_check?authNum=${authNum}`;

    ajaxLoad(url, "GET", null, null, "json")
        .then(data => {
            if (typeof callbackEmailVerifyCheck == 'function') { // 인증 메일 코드 검증 요청 완료 후 처리 콜백
                callbackEmailVerifyCheck(data);
            }
        })
        .catch(err => console.error(err));
};