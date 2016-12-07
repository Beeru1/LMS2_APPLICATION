

(function($){

	// Defining our jQuery plugin

	$.fn.paulund_modal_box = function(prop){

		// Default parameters

		var options = $.extend({
			height : "250",
			width : "500",
			title:"JQuery Modal Box Demo",
			description: "Example of how to create a modal box.",
			top: "20%",
			left: "30%",
		},prop);
				
		//Click event on element
		return this.click(function(e){
			add_block_page();
			add_popup_box();
			add_styles();
			
			$('.paulund_modal_box').fadeIn();
		});
		
		/**
		 * Add styles to the html markup
		 */
		 function add_styles(){			
			$('.paulund_modal_box').css({ 
				'position':'absolute', 
				'left':options.left,
				'top':options.top,
				'display':'none',
				'height': options.height + 'px',
				'width': options.width + 'px',
				'border':'1px solid #fff',
				'box-shadow': '0px 2px 7px #292929',
				'-moz-box-shadow': '0px 2px 7px #292929',
				'-webkit-box-shadow': '0px 2px 7px #292929',
				'border-radius':'10px',
				'-moz-border-radius':'10px',
				'-webkit-border-radius':'10px',
				'background': '#f2f2f2', 
				'z-index':'50',
			});
			$('.paulund_modal_close').css({
				'position':'relative',
				'top':'-25px',
				'left':'20px',
				'float':'right',
				'display':'block',
				'height':'50px',
				'width':'50px',
				'background': 'url(common/images/close1-bg.png) no-repeat',
			});
			
			
			$('.paulund_modal_close1').css({
				'position':'relative',
				'top':'-25px',
				'left':'-69px',
				'float':'right',
				'display':'block',
				'height':'1000px',
				'width':'78px',
				'border':'0px',
				'text-align':'center',
				'text-decoration':'none',
				'font-size':'23px'
				
				
			});
			
			//background:url(    cursor:pointer; font-size:15px; color:#ffffff; text-align:center; text-decoration:none; line-height:35px; font-family:Arial, Helvetica, sans-serif;}
			
			// background:url('background': 'url(common/images/sprite-images.png) no-repeat', cursor:pointer; font-size:15px; color:#ffffff; text-align:center; text-decoration:none; line-height:35px; font-family:Arial, Helvetica, sans-serif;}
			
			/*Block page overlay*/
			var pageHeight = $(window).height();
			var pageWidth = $(window).width();

			$('.paulund_block_page').css({
				'position':'absolute',
				'top':'0',
				'left':'0',
				'background-color':'rgba(0,0,0,0.6)',
				'height':pageHeight,
				'width':pageWidth,
				'z-index':'10'
			});
			$('.paulund_inner_modal_box').css({
				'background-color':'#fff',
				'height':(options.height - 50) + 'px',
				'width':(options.width - 50) + 'px',
				'padding':'10px',
				'margin':'15px',
				'border-radius':'10px',
				'-moz-border-radius':'10px',
				'-webkit-border-radius':'10px'
			});
		}
		
		 /**
		  * Create the block page div
		  */
		 function add_block_page(){
			var block_page = $('<div class="paulund_block_page"></div>');
						
			$(block_page).appendTo('body');
		}
		 	
		 /**
		  * Creates the modal box
		  */
		 function add_popup_box(){
			 var pop_up = $('<div class="paulund_modal_box" ><a href="#" class="paulund_modal_close"></a><div class="paulund_inner_modal_box"><h2>' + options.title + '</h2><p>' + options.description + '</p><br/><br/><br/>'+'<a href="#" align="center" class="paulund_modal_close1"><b>OK</b></a>');
			 //var pop_up1 = $('<div class="paulund_modal_box"><a href="#" class="paulund_modal_close"> OK </a><div class="paulund_inner_modal_box"><h2>' );
			 $(pop_up).appendTo('.paulund_block_page');
			
			 
			 test();	
			 //$(pop_up1).appendTo('.paulund_block_page');
				 
			 $('.paulund_modal_close').click(function(){
				$(this).parent().fadeOut().remove();
				$('.paulund_block_page').fadeOut().remove();
				
			 });
			 $('.paulund_modal_close1').click(function(){
					$(this).parent().fadeOut().remove();
					$('.paulund_block_page').fadeOut().remove();
					
				 });
			 
		}
		 function test () {
			 jQuery("input[type='radio']").attr('disabled', false);
			 
			 jQuery("input[type='radio']").change(function() {
					var productName ='';
					var productLobName ='';
					//jQuery(this).attr('checked', false);
					if(jQuery(this).is(':checked')){
						jQuery('#checkedProductId').html(jQuery(this).attr("title"));
				       }
					 jQuery("input[type='radio']").attr('checked', false);
					 jQuery(this).attr('checked', true);
					 jQuery('.SelectedProduct').val('');
					 jQuery('.SelectedProduct').val(jQuery(this).val());
					 jQuery(".SelectedProduct").attr('checked', true);
					 if(jQuery('.SelectedProduct').val() >0) { 
						 jQuery("#updateLeadId").show();
						 jQuery("#updateLeadIdModule").hide();	
						
					 }
					 
				    });
		 }
		return this;
	};
	
})(jQuery);
