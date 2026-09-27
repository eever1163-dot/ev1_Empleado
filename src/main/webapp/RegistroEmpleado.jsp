<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registro Empleado EV1</title>
	<script src="js/bootstrap.js" type="text/javascript"></script>
	<script src="js/bootstrap.bundle.js" type="text/javascript"></script>
	<script src="js/bootstrap.esm.js" type="text/javascript"></script>
	<script src="js/jquery-4.0.0.min.js" type="text/javascript"></script>

	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="css/bootstrap-grid.css" rel="stylesheet">
	<link href="css/bootstrap-reboot.css" rel="stylesheet">
	<link href="css/bootstrap-utilities.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<h1>Registro de Empleado</h1>
		<form id="formEmpleado" method="post" novalidate >
			<div class="row" style="margin-top: 2%;">
				<div class="col-6">
					<label for="nombre">Nombre</label>
					<input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingrese el nombre" maxlength="50" required>
					<div class="invalid-feedback">Ingrese el nombre</div>
				</div>
				<div class="col-6">
					<label for="apellido">Apellido</label>
					<input type="text" class="form-control" id="apellido" name="apellido" placeholder="Ingrese el apellido" maxlength="50" required>
					<div class="invalid-feedback">Ingrese el apellido</div>
				</div>
			</div>
			<div class="row" style="margin-top: 2%;">
				<div class="col-4">
					<label for="documento">Documento</label>
					<input type="text" class="form-control" id="documento" name="documento" placeholder="Ingrese el documento" maxlength="20" required>
					<div class="invalid-feedback">Ingrese el documento</div>
				</div>
				<div class="col-4">
					<label for="correo">Correo</label>
					<input type="email" class="form-control" id="correo" name="correo" placeholder="Ingrese el correo" maxlength="100" required>
					<div class="invalid-feedback">Ingrese el correo</div>
				</div>
				<div class="col-4">
					<label for="telefono">Teléfono</label>
					<input type="text" class="form-control" id="telefono" name="telefono" placeholder="Ingrese el teléfono" maxlength="20">
				</div>
			</div>
			<div class="row" style="margin-top: 2%;">
				<div class="col-4">
					<label for="cargo">Cargo</label>
					<input type="text" class="form-control" id="cargo" name="cargo" placeholder="Ingrese el cargo" maxlength="80" required>
					<div class="invalid-feedback">Ingrese el cargo</div>
				</div>
				<div class="col-4">
					<label for="salario">Salario</label>
					<input type="number" step="0.01" class="form-control" id="salario" name="salario" placeholder="Ingrese el salario" required>
					<div class="invalid-feedback">Ingrese el salario</div>
				</div>
				<div class="col-4">
					<label for="fecContratacion">Fecha de Contratación</label>
					<input type="date" class="form-control" id="fecContratacion" name="fecContratacion" required>
					<div class="invalid-feedback">Ingrese la Fecha de Contratación</div>
				</div>
			</div>
			<div class="row justify-content-center" style="margin-top: 2%">
				<button class="btn btn-primary" id="btnRegistrar" style="width: 200px">Registrar</button>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$("#btnRegistrar").click(function(e) {
			console.log("click en registrar");
			e.preventDefault(); //Evita que el formulario se envíe automáticamente

			let form = $('#formEmpleado')[0];
	        if (form.checkValidity() === false) {
	            $(form).addClass('was-validated');
	            return;
	        }

	        $.ajax({
				url: 'registraEmpleadoAlias',
				type: 'POST',
				data: $(form).serialize(),
				success: function (response) {

					console.log('response >>> '+ response);
					//limpiar el formulario
					$('#formEmpleado')[0].reset();

					//limpiar las validaciones
					$('#formEmpleado').removeClass('was-validated');

					//enviar un mensaje de éxito al usuario en forma de div que dure 3 segundos
					$('#formEmpleado').prepend('<div class="alert alert-success" role="alert">'+ response.mensajeSalida +'</div>');
					setTimeout(function () {
						$('.alert').remove();
					}, 3000);
				},
				error: function (xhr, status, error) {
					// Manejar errores aquí
					console.error('Error al registrar :', error);
				}
			});
		});
	</script>
</body>
</html>
