
	$(document).ready(function() {
		
		/*		  :::::::::  Search Functionality, get the value, to to the server and grab an xml  ::::::::			*/
		
		var closeSearchResultButton = '<img id="closeSearchResultButton" style="border-radius:3px;margin-auto:0 auto;margin-left:98%;" alt="No Image" src="http://localhost:8080/Baraka/static/resources/gfx/close_search_result_button.png" width="15px" height="15px" />';
		var barakaCommodityImagePopup1 = '<img style="border-radius:3px;margin-auto:0 auto;" class="barakaPopupImgComodOpener" id="';
		var barakaCommodityImagePopup2 = '" alt="No Image" src="http://localhost:8080/Baraka/static/resources/gfx/open_icon_cross.png" width="15px" height="20px" />';
		
		$('#fancySearchInput').keyup(function(){
			$("#searchResultFancyHolder").html("<div id='fancySearchResult'></div>").removeClass("hidden");
			var val = $('#fancySearchInput').val();
			$("#fancyWeather").fadeOut(100, function(){
				$.ajax({
					  url: "http://localhost:8080/Baraka/search/fancy/search/str/" + val, 
					  context: document.body,
					  type:"GET"
					}).done(function(data) {
						if(data === 'undefined' || data.length === 0){
							$("#fancySearchResult").html(closeSearchResultButton + "<br/><div style='margin-top:-18px;padding-left:10px;padding-bottom:20px;'>The item searched could not be found!</div>");
							tieCloseImage();
						}
							var json = jQuery.parseJSON(data);
							var commodities = json.commodities;
							var table = "<table id='fancySearchTableResults'><tr><th >ID</th><th>Name</th><th>Price</th><th ></th></tr>";
							var counter = 0;
							$.each(commodities, function(idx, obj) {
								if(counter > 0){
									table += "<tr>";
									table += "<td>" + obj.id + "</td>";
									table += "<td>" + obj.name + "</td>";
									table += "<td>$" + obj.price + "</td>";
									table += "<td style='text-align:center;'>"+ barakaCommodityImagePopup1 + obj.id + "::::" + obj.name + "::::" + obj.price + "::::" + obj.imageUrl + barakaCommodityImagePopup2 + "</td>";
									table += "<t/r>";
								}
								counter++;
							});
							table += "</table>";
							$("#fancySearchResult").html(closeSearchResultButton + "<br/><div style='margin-top:-18px;padding-left:10px;padding-bottom:20px;'>"+ table + "</div>");
							tieCloseImage();
							$(".barakaPopupImgComodOpener").click(function(){
								var details = $(this).attr('id');
								openCommodity(details);
							});
							return ;
					}).fail(function(data){
							$("#fancySearchResult").html(closeSearchResultButton + "<br/><div style='margin-top:-18px;padding-left:10px;padding-bottom:20px;'>An error occurred!</div>");
							tieCloseImage();
							return;
				});
				
				$("#fancySearchResult").fadeIn(200);				
			});
		}).keyup();
		$("#searchResultFancyHolder").addClass('hidden');
		
		
		getBarakaWeatherDataAndHandleEventAndUI(0);
		$("#fancyPopupDiv").hide();
		
		var runningEvent = 0;
		
		$("#refreshBarakaWeatherIcon").click(function(){
			runningEvent = setInterval(runRotateIcon, 20);
         	setTempEventUI();
       		getBarakaWeatherDataAndHandleEventAndUI(runningEvent);
        });
		
		// found in fancyBindings.js
		bindFancyBindings();
		
		
		$("#fancyPopupDiv").mouseleave(function(){
			$(this).slideToggle(300,function(){
				$(this).css("left","0%");
			});
		});
		
		$("#barakaJobsMenuName_apply").click(function(){
			window.location.href = "http://localhost:8080/Baraka/jobs/apply";
		});
		
		$("#barakaJobsMenuName_admin").click(function(){
			window.location.href = "http://localhost:8080/Baraka/admin";
		});
		
		$("#barakaJobsMenuName_createCommodity").click(function(){
			window.location.href = "http://localhost:8080/Baraka/admin/commodity/new/create";
		});
		
		$("#barakaJobsMenuName_kendo_tree").click(function(){
			window.location.href = "http://localhost:8080/Baraka/kendo/tree";
		});
		
		$("#loginToAccountConfirmation").click(function(){
			$("#newOrExistingApplicant").fadeOut(function(){
				window.location.href = "/Baraka/login/main";
			});
		});
		
		$("#createAccountConfirmation").click(function(){
			performAccountCreation();
		});
		
		$("#barakaJobsMenuName_kendo_ui").click(function(){
			window.location.href = "/Baraka/kendo/grid";
		});
		
		
		
		
    });
	
	var degrees = 0;
	var weatherData;
	var backgroundImages = ["cloudy-very-sunny-gt-50.jpg","beautiful-winter-scenewallpapers.jpg","very-sunny.jpg","beautiful-winter-scenery-2_00449551.jpg","snowy_sunny.jpg","sky.jpg","stormy-lightning-island.jpg","SunLight.jpg"];
	var backgroundImageIndex = 0;
	var errorStatus = false;
	
	function getBackgroundImage(){
		if(backgroundImageIndex === (backgroundImages.length - 1))
			backgroundImageIndex = 0;
		else
			backgroundImageIndex++;
		return backgroundImages[backgroundImageIndex];
	}
	
	var isRotaterAdded = false;
	
	function openCommodity(content){
			var contents = content.split("::::");
			//obj.id + "::::" + obj.name + "::::" + obj.price + "::::" + obj.imageUrl
			var id = contents[0];
			var name = contents[1];
			var price = contents[2];
			var imageUrl = contents[3];
			
			var closeMiniPopupImage = '<img id="closeMiniPopupImage" style="border-radius:3px;margin-auto:0 auto;margin-left:94.5%;" alt="No Image" src="http://localhost:8080/Baraka/static/resources/gfx/close_search_result_button.png" width="15px" height="15px" />';
			var commodityImage1 = '<img style="margin-auto:0 auto;" class="commodityImageMiniPopupClass" id="';
			var commodityImage2 = '" alt="No Image" width="100%" height="75%" src="http://localhost:8080/Baraka/static/resources/gfx/'+ imageUrl + '" />';
			var minPopup = closeMiniPopupImage + "<div id='miniPopupImgHolder'>" + commodityImage1 + commodityImage2 + "</div><div id='miniPopupImgHoldFormSection' style='background-color:#91B7D0;height:25%;'><table style='width:100%;margin-left:0%;height:100%;text-align:center;border:1px solid #A3B2BC;' ><tr><td style='font-size:16px;'> "+ name  +" : <span style='color:red;'>$" + price + "</span></td><td id='highlighToBuy' >Add to Cart</td></tr></table></div>";
			barakaPopupDive(minPopup);
			$("#highlighToBuy").click(function(){
				$("#miniPopupImgHoldFormSection").remove();
				$("#miniPopupImgHolder").remove();
				$("#barakaPopupMessage").css('background-color','#FFFFFF');
				$("#barakaPopupMessage").css('border','1px solid #91BDBA');
				$("#barakaPopupMessage").append("<div style='color:green;margin-top:15%;text-align:center;font-size:20px;'><span style='color:green;margin-top:30%;'>Successfully added to Cart!</span></div>");
				$("#barakaPopupMessage").fadeOut(3000);
			});
	}
	
	function barakaPopupDive(html){
		var div = "<div id='barakaPopupMessage' style='width:300px; height:150px;margin-top:165px;margin-left:73%;background-color:#7CB8DF;border:1px solid #1B3547; border-radius:4px;opacity:0.7;;position:absolute;z-index:4000;' >"+ html +"</div>";
		$("#barakaPopupMessage").remove();
		$("body").append(div); 
		$("#barakaPopupMessage").hide();
		$("#barakaPopupMessage").slideToggle(600);
		$("#closeMiniPopupImage").click(function(){
			$("#barakaPopupMessage").fadeOut(1000, function(){
				$("#barakaPopupMessage").remove();
			});
		});
	}
	
	function barakaPopupDiveAtomic(html){
		var closeMiniPopupImage = '<img id="closeMiniPopupImageAtomic" style="border-radius:3px;margin-auto:0 auto;margin-left:95%;" alt="No Image" src="http://localhost:8080/Baraka/static/resources/gfx/closeWindow.png" width="15px" height="15px" />';
		var div = "<div id='barakaPopupMessage' style='width:300px; height:150px;margin-top:100px;margin-left:83%;background-color:#E3EEE5;border:1px solid #A0BFA6; border-radius:4px;opacity:0.7;;position:absolute;z-index:4000;' >" + closeMiniPopupImage + html +"</div>";
		$("#barakaPopupMessage").remove();
		$("body").append(div); 
		$("#barakaPopupMessage").hide();
		$("#barakaPopupMessage").slideToggle(600);
		$("#closeMiniPopupImageAtomic").click(function(){
			$("#barakaPopupMessage").fadeOut(1000, function(){
				$("#barakaPopupMessage").remove();
			});
		});
	}
	
	function barakaPopupDiveForImage(html){
		var closeMiniPopupImage = '<img id="closeMiniPopupImagForImage" style="border-radius:3px;margin-auto:0 auto;margin-left:96%;" alt="No Image" src="http://localhost:8080/Baraka/static/resources/gfx/closeWindow.png" width="15px" height="15px" />';
		var div = '<div id="imageContainer">'+ closeMiniPopupImage +'<img style="border-radius:3px;margin-left:2%;" alt="No Image" src="http://localhost:8080/Baraka/static/resources/gfx/' + html + '" width="96%"; height="80%"; /></div>';
		$("#imageContainer").remove();
		$("body").append(div); 
		$("#imageContainer").hide();
		$("#imageContainer").fadeIn(1000);
		$("#closeMiniPopupImagForImage").click(function(){
			$("#imageContainer").fadeOut(400,function(){
				$("#imageContainer").remove();
			});
		});
	}
	
	function tieCloseImage(){
		$("#closeSearchResultButton").click(function(){
			$("#searchResultFancyHolder").empty();
			$("#fancyWeather").fadeIn(1000);
		});
	}
	
	function runRotateIcon(){
		rotateIcon();
		if(degrees <= 360){
			degrees = degrees + 33;
		}else{
			degrees = 0;
		}
	}
	
	
	function updateImage(id, imageName){
			$(id).attr("src", "http://localhost:8080/Baraka/static/resources/gfx/" + imageName);
	}
	
	function updateImageFromOut(id, imageName){
		$(id).attr("src", imageName);
	}
	
	
	function updateToday(data){
		
		$( "#barakaWeatherLocation" ).text( data.barakaWeatherLocation );
		$( "#todayDayMonthAndDate" ).text( data.todayDayMonthAndDate );
		$( "#todayDateSuperscript" ).text( data.todayDateSuperscript );
		$( "#todayDateYear" ).text( data.todayDateYear );
		$( "#todayTimeRightNow" ).text( data.todayTimeRightNow );
		$( "#todayWeatherStatusDescription" ).text( data.todayWeatherStatusDescription );
		$( "#todayTempRightNow" ).text( data.todayTempRightNow );
		$( "#todayTempLow" ).text( data.todayTempLow );
		$( "#todayTempHigh" ).text( data.todayTempHigh );
		$( "#todayTempRealFeel" ).text( data.todayTempRealFeel );
		$( "#tempMeasurement" ).text( data.tempMeasurement );
		
		updateImageFromOut("#todayWeatherStatusIcon img", data.icon );
		
		$("#fancyWeather").css('background-color','rgba(0,0,0,0.8)');
		
		$("#fancyWeather").fadeOut(200,function(){
			$(this).css('background-image','url("http://localhost:8080/Baraka/static/resources/gfx/' + getBackgroundImage() + '\")');
		     $(this).fadeIn(1200);
	     });
		
		$("#fancyWeather").css('background-color','rgba(0,0,0,0.00001)');
		 
	}
	
	function updateAfterDay1(data){
		$( "#dayAfter1NameAndDate" ).text( data.dayAfter1NameAndDate );
       	$( "#dayAfter1NameAndDateSuperscript" ).text( data.dayAfter1NameAndDateSuperscript );
       	$( "#dayAfter1Description" ).text( data.dayAfter1Description);
       	$( "#dayAfter1High" ).text( data.dayAfter1High );
       	$( "#dayAfter1Low" ).text( data.dayAfter1Low ); 
       	updateImageFromOut("#dayAfter1IconTd img", data.icon );
	}
	
	function updateAfterDay2(data){
		$( "#dayAfter2NameAndDate" ).text( data.dayAfter2NameAndDate );
       	$( "#dayAfter2NameAndDateSuperscript" ).text( data.dayAfter2NameAndDateSuperscript );
       	$( "#dayAfter2Description" ).text( data.dayAfter2Description);
       	$( "#dayAfter2High" ).text( data.dayAfter2High );
       	$( "#dayAfter2Low" ).text( data.dayAfter2Low ); 
       	updateImageFromOut("#dayAfter2IconTd img", data.icon );
	}
	
	function updateAfterDay3(data){
		$( "#dayAfter3NameAndDate" ).text( data.dayAfter3NameAndDate );
       	$( "#dayAfter3NameAndDateSuperscript" ).text( data.dayAfter3NameAndDateSuperscript );
       	$( "#dayAfter3Description" ).text( data.dayAfter3Description);
       	$( "#dayAfter3High" ).text( data.dayAfter3High );
       	$( "#dayAfter3Low" ).text( data.dayAfter3Low ); 
       	updateImageFromOut("#dayAfter3IconTd img", data.icon );
	}
	
	function updateAfterDay4(data){
		$( "#dayAfter4NameAndDate" ).text( data.dayAfter4NameAndDate );
       	$( "#dayAfter4NameAndDateSuperscript" ).text( data.dayAfter4NameAndDateSuperscript );
       	$( "#dayAfter4Description" ).text( data.dayAfter4Description);
       	$( "#dayAfter4High" ).text( data.dayAfter4High );
       	$( "#dayAfter4Low" ).text( data.dayAfter4Low ); 
       	updateImageFromOut("#dayAfter4IconTd img", data.icon );
	}
	
	function getBarakaWeatherDataAndHandleEventAndUI(runningIntervalInput){
		
		$.ajax({
			
			  url: "http://localhost:8080/Baraka/weather/barakaWeather", 
			  context: document.body,
			  type:"GET",
			  
			}).done(function(data) {
				
				if(errorStatus === true){
					$("#refreshText").text("Refresh");
					$("#refreshText").css("color","white");
				}
				
	       		var json = JSON.parse(data);
	       		var todayJson = json.today[0];
	       		var after_day_1_Json = json.after_day_1[0];
	       		var after_day_2_Json = json.after_day_2[0];
	       		var after_day_3_Json = json.after_day_3[0];
	       		var after_day_4_Json = json.after_day_4[0];
	       		
	       		$(".tempMeasurement").text(todayJson.tempMeasurement);

	       		updateToday(todayJson);
	           	updateAfterDay1(after_day_1_Json);
	           	updateAfterDay2(after_day_2_Json);
	           	updateAfterDay3(after_day_3_Json);
	           	updateAfterDay4(after_day_4_Json);
	           	
				if(runningIntervalInput !== 0){
					clearTempEventUI();
					clearInterval(runningIntervalInput);
				}
	           	
				errorStatus = false;
				
	           	return;
	           	
			}).fail(function(data){
				clearTempEventUI();
				clearInterval(runningIntervalInput);
				$("#refreshText").text("Error!");
				$("#refreshText").css("color","yellow");
				errorStatus = true;
		});
	}
	
	function rotateIcon(){
		$("#refreshBarakaWeatherIcon").css('-moz-transform', 'rotate(' + degrees + 'deg)');
		$("#refreshBarakaWeatherIcon").css('-moz-transform-origin', '50% 50%');
		$("#refreshBarakaWeatherIcon").css('-webkit-transform', 'rotate(' + degrees + 'deg)');
		$("#refreshBarakaWeatherIcon").css('-webkit-transform-origin', '50% 50%');
		$("#refreshBarakaWeatherIcon").css('-o-transform', 'rotate(' + degrees + 'deg)');
		$("#refreshBarakaWeatherIcon").css('-o-transform-origin', '50% 50%');
		$("#refreshBarakaWeatherIcon").css('-ms-transform', 'rotate(' + degrees + 'deg)');
		$("#refreshBarakaWeatherIcon").css('-ms-transform-origin', '50% 50%');
	}
	
	function setTempEventUI(){
		$("#fancyWeatherTodayDetails").css('background-color','rgba(0,0,0,0.7)');
   		$("#fancyWeatherFocusDetails").css('background-color','rgba(0,0,0,0.7)');
   		$("#refreshBarakaWeatherIcon").attr("width", "12px");
    	$("#refreshBarakaWeatherIcon").attr("height", "12px");
	}
	
	function clearTempEventUI(){
		$("#fancyWeatherTodayDetails").css('background-color','rgba(0,0,0,0.5)');
    	$("#fancyWeatherFocusDetails").css('background-color','rgba(0,0,0,0.5)');
    	$("#refreshBarakaWeatherIcon").attr("width", "18px");
    	$("#refreshBarakaWeatherIcon").attr("height", "18px");
	}
	
	function getLoginFormString(){
		return "<form action='http://localhost:8080/Baraka/jobs/login' method='POST' id='jobsLoginForm' class='loginForms' >" + 
			"<table><tr><td>User Name</td><td><input type='text' name='userName'></input></td></tr>" +
		    "<tr'><td>Password</td><td><input type='text' name='password'></input></td></tr>" + 
		    "<tr><td></td><td><button onClick='submitJobsLogin()' type='submit' class='loginButton'>Login</button>"+ 
		    "<button  onClick='function reload(){location.reload()}' type='text' class='loginButton'>Cancel</button></td>" +
			"</tr></table></form>";
	}