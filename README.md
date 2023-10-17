# 4_AndroidIntermediate_JeffrySusilo

1.Fitur Autentikasi:

1.1 Halaman Login (Login Page)

  Input Email (R.id.ed_login_email)
  Input Password (R.id.ed_login_password)
  Halaman Registrasi (Register Page)
  
  Input Nama (R.id.ed_register_name)
  Input Email (R.id.ed_register_email)
  Input Password (R.id.ed_register_password) - Password harus disembunyikan

1.2 Custom View EditText pada halaman Login dan Registrasi:

  Validasi password kurang dari 8 karakter dengan pesan error langsung pada EditText
  Penyimpanan Data Sesi dan Token di Preferences:
  
  Data Sesi untuk mengatur alur aplikasi: jika sudah login, langsung masuk ke halaman utama; jika belum, masuk ke halaman login
  Fitur Logout (R.id.action_logout) pada halaman utama: menghapus informasi token dan sesi

2.Fitur Daftar Cerita (List Story):

2.1 Menampilkan daftar cerita dari API dengan minimal informasi:

  Nama user (R.id.tv_item_name)
  Foto (R.id.iv_item_photo)
  Tampilan detail cerita:
  
  Ketika salah satu item cerita ditekan, tampilkan detail cerita dengan minimal informasi:
  Nama user (R.id.tv_detail_name)
  Foto (R.id.iv_detail_photo)
  Deskripsi (R.id.tv_detail_description)

3. Fitur Tambah Cerita:

3.1 Halaman untuk menambah cerita baru yang dapat diakses dari halaman daftar cerita:

  Input file foto (wajib bisa dari galeri)
  Input Deskripsi cerita (R.id.ed_add_description)

3.2Ketentuan dalam menambahkan cerita baru:

  Tombol (R.id.button_add) untuk upload data ke server
  Setelah proses upload berhasil, kembali ke halaman daftar cerita dengan data cerita terbaru muncul di paling atas

4.Fitur Menampilkan Animasi:

Membuat animasi pada aplikasi dengan menggunakan salah satu jenis animasi:
Property Animation
Motion Animation
Shared Element
Dengan fitur-fitur ini, aplikasi akan memiliki kemampuan autentikasi, tampilan daftar cerita, kemampuan menambah cerita baru, dan elemen animasi untuk meningkatkan pengalaman pengguna.




