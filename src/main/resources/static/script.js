//Función para mostrar el mensaje de confirmación o fallo y que se quite desvaneciendose a los 3 segundos de mostrarse
setTimeout(function() {
        let alertMessages = document.querySelectorAll('.alert');
        alertMessages.forEach(function(alert) {
            alert.style.transition = "opacity 0.5s ease-out";
            alert.style.opacity = "0";
            setTimeout(() => alert.remove(), 500);
        });
    }, 3000);
	
	
//Función para ir a la página anterior
function goBack() {
	            if (document.referrer && document.referrer !== window.location.href) {
	                window.history.back();
	            } else {
	                window.location.href = '/invitado'; 
	            }
	        }	
