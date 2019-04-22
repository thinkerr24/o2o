Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

$(function() {
	var productId = getQueryString('productId');
	var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId=' + productId;

	$.getJSON(
		productUrl,function(data) {
			if (data.success) {
				var product = data.product;
				$('#product-img').attr('src', product.imgAddr);
				$('#product-time').text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
				$('#product-name').text(product.productName);
				$('#product-desc').text(product.productDesc);
				// 获取商品详情图片列表
				var productDetailImgList = product.productImgList;
				var swiperHtml = '';
				productDetailImgList.map(function(item, index) {
					swiperHtml += ''
                        + '<div class="swiper-slide img-wrap">'
                        +      '<img class="banner-img" src="'+ item.imgAddr +'" alt="'+ item.piImgDesc +'">'
                        + '</div>';
				});
			
				$('.swiper-wrapper').html(swiperHtml);
				 // 设置轮播图轮换时间为1秒
	            $(".swiper-container").swiper({
	                autoplay: 1000,
	                // 用户对轮播图进行操作时，是否自动停止autoplay
	                autoplayDisableOnInteraction: true
	            });
			}
		});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});