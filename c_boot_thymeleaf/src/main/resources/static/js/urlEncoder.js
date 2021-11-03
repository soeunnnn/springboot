/**
 * 
 */
 
 let urlEncoder = paramObj => { //요청 파라미터 값 받아옴
	
	let arr = [];
	
	for(key in paramObj){
		let param = key + '=' + encodeURIComponent(paramObj[key]);
		arr.push(param);
	}
	
	return arr.join('&');
}