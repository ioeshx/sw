/* 本页面为检验规则的具体实现 */

/* 用户名：仅能出现英⽂字符、数字与下划线；用户名长度为3-10 */
export function validateUsername(username){
    const reg = /^[a-zA-Z0-9_]{3,10}$/;
    return reg.test(username)
}

/* 电话号码 */
export function validatePhone(phone) {
    /* 空值判断在vue中进行 */
    const reg = /^1[3-9]\d{9}$/
    return reg.test(phone)
}
  
/* 身份证 */
export function validateIdnum(idnum){
    const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    return reg.test(idnum)
}

/* 邮箱 */
export function validateEmail(email){
    const reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    return reg.test(email)
}

/* 密码:密码⻓度为6-32个字符； 字⺟，数字或者特殊字符（-_）⾄少包含两种 */
export function validatePassword(password){
    const reg = /^[a-zA-Z\d_-]{6,32}$/;
    if(!reg.test(password)){
        return false;
    }
    const reg1 = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[-_])[a-zA-Z\d_-]{6,32}$/;
    if(reg1.test(password)){
        return true;
    }
    const reg2 = /^(?=.*[a-zA-Z])(?=.*[-_])|(?=.*\d)(?=.*[-_])|(?=.*\d)(?=.*[A-Za-z])[a-zA-Z\d_-]{6,32}$/;
    return reg2.test(password)
}