<template>
	<transition name="modal" id="modal-template">
		<div class="modal-mask">
			<div class="modal-wrapper">
				<div class="modal-container">
					<div class="modal-header">
						<slot name="header"> </slot>
					</div>

					<div class="modal-body">
						<slot name="body"> </slot>
					</div>

					<div class="modal-footer">
						<!-- default footer -->
						<slot name="footer"> </slot>
					</div>
				</div>
			</div>
		</div>
	</transition>
</template>
<script>
export default {
	data: () => ({}),
	methods: {}
};
</script>
<style>
/* 모달 관련 css */
.modal-mask {
	position: fixed;
	z-index: 9998;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	display: table;
	transition: opacity 0.1s ease;
}

.modal-wrapper {
	display: table-cell;
	vertical-align: middle;
}

.modal-container {
	width: 350px;
	/* height: 220px; */
	margin: 0px auto;
	padding: 20px 40px 20px 40px;
	background-color: #fff;
	border-radius: 2px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
	transition: all 0.1s ease;
	/* font-family: Helvetica, Arial, sans-serif; */
}

.modal-header {
	margin-top: 0;
	font-size: 20px;
	font-weight: 500;
}

.modal-body {
	margin: 20px 0;
	/* height: 80px; */
}
.modal-default-button {
	margin-left: 15px;
	padding: 5px 10px 5px 10px;
	border-radius: 3px;
	background-color: rgba(160, 23, 98, 0.5);
	color: white;
}
.modal-default-button:hover {
	background-color: rgba(160, 23, 98, 0.8);
}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
	opacity: 0;
}

.modal-leave-active {
	opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
	-webkit-transform: scale(1.1);
	transform: scale(1.1);
}
</style>
