/**
 * 파일 업로드, 삭제, 조회 공통 기능
 */

const fileManager = {
    //파일 업로드
    upload(files, gid, location) {
        
    },
    //파일 삭제
    delete() {
        
    },
    //파일 조회
    search() {
        
    }
};

window.addEventListener("DOMContentLoaded", function() {

    //파일 업로드 버튼 이벤트 처리 S
    const fileUploads = document.getElementsByClassName("fileUploads");
    const fileEl = document.createElement("input");
    fileEl.type='file';
    fileEl.multiple = true;
    //파일 데이터만 전송해준다.

    for (const el of fileUploads) {
        el.addEventListener("click", function(){
            fileEl.value = "";
            delete fileEl.gid;
            delete fileEl.location;
            //기존 값과 충돌 발생 가능성 있으므로 기존 값 지우기

            const dataset = this.dataset;
            fileEl.gid = dataset.gid;
            if (dataset.location) fileEl.location = dataset.location;

            fileEl.click();
        });
    }
    //파일 업로드 버튼 이벤트 처리 E

    //파일 업로드 처리
    fileEl.addEventListener("change", function (e) {
       //이벤트 객체에 선택한 파일 정보 담김
       const files = e.target.files;
        fileManager.upload(files, fileEl.gid, fileEl.location);
    });

});