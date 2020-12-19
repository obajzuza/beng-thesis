import qrcode
qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_Q,
        box_size=10,
        border=3,
    )
qr.add_data('4')
qr.make(fit=True)

image = qr.make_image(fill_color="#74CBE3", back_color="white")
image.save('4.png')