# Tutorial APAP

## Authors
* **Alizha** - *2106652000* - *B*

## Tutorial 8

### What I Have Learned Today

- Mempelajari bagaimana mengimplementasikan security di SpringBoot.
- Mempelajari bagaimana mengimplementasikan autentikasi menggunakan SSO UI dan JWT.

1. Apa perbedaan antara encryption dan hashing? Mana yang lebih baik untuk penyimpanan password?

Perbedaan antara encryption dan hashing terletak pada tujuan penggunaannya. Encryption melibatkan proses mengubah teks biasa menjadi teks tidak terbaca yang dapat diubah kembali menggunakan kunci deskripsi, sedangkan hashing menghasilkan kode (hash)unik yang tidak dapat dikembalikan ke bentuk aslinya. Dalam konteks penyimpanan password, hashing lebih disarankan karena sifatnya yang satu arah. Ketika password di-hash, tidak mungkin untuk mengembalikan password asli dari nilai hash, sehingga dapat meningkatkan kemanan dari *password*. 

2. Apa yang membuat spring meredirect pengguna ke /login ketika pertama kali membuka localhost:8080?

Terdapat pengaturan pada metode .formLogin() pada konfigurasi Spring Security yang dapat memberitahu Spring Security untuk mengarahkan pengguna ke halaman login ("/login") jika mereka belum terautentikasi dan mencoba mengakses halaman yang memerlukan autentikasi. Dengan demikian, ketika membuka http://localhost:8080, Spring Security secara otomatis menerapkan pengalihan ke halaman login sesuai dengan konfigurasi tersebut, sehingga pengguna diarahkan ke halaman login ketika pertama kali membuka aplikasi.

3. Kapan method loadUserByUsername ini dipanggil?

Method tersebut akan dipanggil saat proses autentikasi pengguna dijalankan. Saat seorang pengguna berusaha melakukan login, Spring Security melakukan pencarian pengguna berdasarkan username yang diinputkan, di mana proses tersebut nantinya akan  melibatkan pemanggilan method `loadUserByUsername` untuk mengambil informasi pengguna dari database dan mengembalikan objek `UserDetails` yang digunakan oleh Spring Security untuk memeriksa kecocokan password dan peran pengguna selama proses autentikasi.

4. Apa makna dari anotasi order serta mengapa jwtFilterChain ada di order 1 dan webFilterChain ada di order 2?

Anotasi `@Order` digunakan untuk menentukan urutan dari konfigurasi, di mana semakin kecil nilai dari parameter `@Order`, semakin tinggi prioritasnya untuk dieksekusi. Urutan filter yang lebih rendah pada jwtFilterChain memastikan bahwa filter JWT dieksekusi sebelum filter keamanan standar dalam Spring Security. Hal tersebut penting karena filter JWT menangani autentikasi berdasarkan token JWT, memastikan bahwa mekanisme autentikasi berbasis token berjalan sebelum proses keamanan lebih lanjut yang diwakili oleh WebFilterChain.

5. Apa itu Mono? Apa kegunaan Mono?

Mono merupakan objek dalam pemrograman reaktif, khususnya dalam framework Project Reactor di Java, yang berperan sebagai publisher yang dapat mengembalikan 0 (empty) atau 1 (single) value. Dengan demikian, Mono Sangat berguna untuk skenario seperti pengambilan data dari database atau panggilan ke API jaringan, memodelkan aliran data yang responsif terhadap perubahan, dan memudahkan pengembangan kode yang adaptif terhadap peristiwa asinkron.
       
### What I did not understand
[x] 

## Tutorial 7

### What I Have Learned Today

- Mempelajari cara melakukan dockerisasi pada *project* bacabaca.

- Mempelajari bagaimana cara melakukan *load balancing* menggunakan Nginx.

- Mempelajari penggunaan CI/CD pada Gitlab.

1. Apa itu Dockerfile dan docker-compose.yaml? Apa fungsinya?

- Dockerfile: *file* teks yang berisi serangkaian perintah yang digunakan oleh *user* yang akan dieksekusi secara berurutan untuk mengotomatisasi pembuatan sebuah docker *image* yang di mana *image* nantinya akan digunakan untuk menjalankan Docker *container*.

- docker-compose.yaml: *file* konfigurasi YAML yang digunakan oleh Docker Compose untuk mendefinisikan layanan, jaringan, volume, dan konfigurasi lainnya yang dibutuhkan untuk menjalankan aplikasi Docker yang terdiri dari beberapa kontainer secara bersama-sama, membentuk suatu lingkungan terisolasi.

2. Screenshot hasil perubahan anda. Setelah anda menyelesaikan tutorial ini, menurut anda, mengapa kita perlu mengganti port?

- Sebelum perubahan:
       ![sebelum](https://cdn.discordapp.com/attachments/1151860115629150228/1175460449077174343/image.png?ex=656b4fda&is=6558dada&hm=3e81aa0942835eff6d00c0924ab10862d89aede1215d7ff0d9842f8c0cba0fb2&)

- Setelah perubahan:
       ![setelah](https://cdn.discordapp.com/attachments/1151860115629150228/1175461294162321461/image.png?ex=656b50a3&is=6558dba3&hm=6cbaaf60bc1f3d6a7128a143af3b20d0c2522ad618253f0917682d9efd403606&)

Port perlu diganti karena aplikasi bacabaca akan dideploy di satu server yang sama dengan teman-teman yang lainnya yang menyelesaikan tutorial ini, yaitu di server kirti. Oleh karena itu, port dari setiap orang harus dibuat unik agar tidak ada konflik port, sehingga aplikasi dapat berjalan dan beroperasi secara independen tanpa saling mengganggu aplikasi yang lainnya.

3. Apa saja yang terjadi di langkah ini?

Saat menjalankan perintah docker-compose up, Docker akan membangun dan menjalankan kontainer-kontainer untuk layanan web dan web2 berdasarkan konfigurasi yang telah diatur. Kontainer-kontainer tersebut kemudian dijalankan, memberikan akses melalui port yang ditentukan (10150 untuk web dan 10151 untuk web2). Selain itu, Docker juga akan memulai kontainer layanan database (db) dengan menggunakan gambar PostgreSQL. 

4. Sertakan screenshot container yang sedang berjalan (versi gui atau cli, pilih salah satu). Apa itu docker images, docker container, dan docker volume?

![docker](https://cdn.discordapp.com/attachments/1151860115629150228/1175463526450602034/image.png?ex=656b52b7&is=6558ddb7&hm=7156478f5f5886cd87e5bea273c4cf72ef6ba040ae5270066ea5fba75da91aed&)

- docker images: *template* atau *blueprint* yang berisi komponen-komponen yang diperlukan untuk membuat suatu container, seperti *source code*, *dependencies*, dan pengaturan lainnya yang diperlukan untuk menjalankan suatu aplikasi. 

- docker container: *environment* untuk mengemas aplikasi bersamaan dengan segala yang diperlukan untuk menjalankannya, mencakup docker images, seperti system tools, library, code, runtime dan konfigurasi.

- docker volume: sistem file independen yang sepenuhnya dikelola oleh Docker dan berada sebagai berkas atau direktori biasa pada host yang berfungsi sebagai tempat penyimpanan data yang persisten yang dapat diakses dan digunakan oleh container. 

5. Apa perbedaan docker-compose down dan docker stop?

*command* 'docker-compose stop' digunakan untuk menghentikan kontainer yang sedang berjalan tanpa menghapusnya. Sebaliknya, *command* 'docker-compose down' tidak hanya menghentikan, tetapi juga menghapus kontainer beserta jaringan terkait yang digunakan. 

6. Sertakan screenshot mengakses laman kirti milik anda melalui browser (seperti screenshot di atas)

![kirti](https://cdn.discordapp.com/attachments/1151860115629150228/1175472068603695124/image.png?ex=656b5aac&is=6558e5ac&hm=2ede22d343a6e825136c3e1157d38856b8f40deb5532d642e1cae41ac9883aed&)

7. Ceritakan pengalaman anda melakukan deployment ke Kirti. Kendala apa yang anda alami?

Saya tidak mengalami kendala saat melakukan *deployment* ke Kirti.

9. Buka container docker Anda, lalu screenshot. Apa perbedaan tampilan container sekarang dengan tampilan container pada langkah tutorial docker di awal tadi?

![docker](https://cdn.discordapp.com/attachments/1151860115629150228/1175472401040027658/image.png?ex=656b5afb&is=6558e5fb&hm=7e37633c827e85d1183e3b091d69aa6a3471ba713ebc223040bb36b5d499b1ab&)

Jika dilihat dari gambar di atas, terdapat penambahan satu docker *container*, sehingga sekarang jumlahnya menjadi tiga, yaitu db-1, web-1, dan web2-1, dari yang sebelumnya hanya terdapat dua *container*, yaitu db-1 dan web-1. 

10. Sertakan screenshot tampilan web ketika pertama kali menjalankan localhost:9090/port dan tampilan web ketika halaman di-refresh.

- Pertama kali menjalankan localhost:9090/port

![pertama](https://media.discordapp.net/attachments/1151860115629150228/1175489821582557275/image.png?ex=656b6b35&is=6558f635&hm=1db378894387d1952d56e6251571d94e5153220ecb0d832f69e61e157c2afaae&=&width=1057&height=522)

- Setelah di*refresh*

![kedua](https://media.discordapp.net/attachments/1151860115629150228/1175489883394027641/image.png?ex=656b6b43&is=6558f643&hm=cb0bcb6377c467558eb9de7dbe3e4d8b69ee7c34269047ee18ddcdf42bb93c96&=&width=1057&height=526)

11. Apa yang dimaksud load balancing?  Pada langkah keberapa kita mengatur konfigurasi untuk load balancing? Jelaskan blok baris yang mengatur hal tersebut.

*Load balancing* adalah proses mendistribusikan *traffic* beban kerja di antara berbagai server-server dari satu aplikasi yang sama yang bertujuan untuk membantu menjaga beban kerja aplikasi agar lebih seimbang atau terdistribusi dengan merata. Dengan *load balancing*, jika salah satu server mengalami masalah atau kelebihan beban, *load balancer* akan mengalihkan *traffic* ke server lain yang masih dapat menangani permintaan. Konfigurasi untuk *load balancing* diatur pada langkah ke sembilan yang terdapat pada blok baris berikut pada *file* nginx.conf:

       upstream homepage {
              server localhost:10150;
              server localhost:10151;
       }

Upstream homepage merupakan grup server yang akan dilakukan *load balancing* yang memuat server localhost:10150 sebagai konfigurasi server pertama dan localhost:10151 sebagai konfigurasi server kedua atau cadangan dari grup ketika server pertama mengalami *load* yang tinggi.

Dari konfigurasi tersebut, Nginx akan mendistribusikan trafik secara round-robin antara server-server yang terdaftar dalam grup "homepage". Jika salah satu server mati atau tidak dapat melayani permintaan, Nginx akan secara otomatis mengarahkan permintaan ke server lainnya.

12. Apa yang dimaksud reverse proxy?  Pada langkah keberapa kita mengatur konfigurasi untuk reverse proxy? Jelaskan baris yang mengatur hal tersebut dan jelaskan kegunaannya dalam pengerjaan tugas kelompok nanti.

Reverse proxy adalah jenis server proxy yang berada di belakang firewall dalam jaringan *private* yang berfungsi sebagai perantara antara klien dan server *backend*. Reverse proxy menerima permintaan dari klien dan meneruskannya ke server *backend* yang sesuai. Konfigurasi untuk reverse proxy diatur pada langkah ke sembilan yang terdapat pada baris berikut pada *file* nginx.conf:

       location / {
              proxy_pass "http://homepage";
       }

location / merupakan root path di mana permintaan akan diarahkan dan `proxy_pass "http://homepage";` merupakan grup server yang telah dikonfigurasi sebelumnya pada bagian upstream yang menjadi grup server di mana nginx akan meneruskan reverse proxy.

Pada tugas kelompok, reverse proxy berguna untuk memfasilitasi pengelolaan *traffic* aplikasi agar lebih optimal, teratur, dan aman di dalam lingkungan yang terdistribusi. Selain itu, reverse proxy juga dapat berperan sebagai penyedia lapisan keamanan tambahan karena kemampuannya dalam melindungi detail teknis dari server *backend*.

13. Kendala apa yang anda hadapi ketika melakukan tutorial bagian nginx?

       Tidak ada.

14. Apa fungsi dari SSH keys yang Anda buat dengan menggunakan ssh-keygen? Apa perbedaan antara file ~/.ssh/deployer_apap.pub dan ~/.ssh/deployer_apap ?

       SSH keys yang dibuat dengan menggunakan ssh-keygen berfungsi sebagai autentikasi yang memungkinkan kita untuk masuk ke server tanpa harus mengetikkan kata sandi setiap kali. Perbedaan antara file ~/.ssh/deployer_apap.pub dan ~/.ssh/deployer_apap terletak pada jenis kunci yang disimpan di dalamnya. ~/.ssh/deployer_apap.pub merupakan *file public key* yang isinya dapat dibagikan ke server yang akan diakses untuk memverifikasi identitas pengguna, sedangkan ~/.ssh/deployer_apap merupakan *file private key* yang disimpan secara lokal dan bersifat rahasia untuk membuktikan identitas pengguna saat terhubung ke server.

15. Apa perbedaan antara GitLab repository dan GitLab runner?

       Gitlab repository merupakan lokasi di mana kode-kode yang saling terkait disimpan, dikelola, dan digunakan untuk suatu proyek. Sementara itu, Gitlab runner merupakan aplikasi yang berfungsi bersama dengan GitLab CI/CD untuk menjalankan jobs dalam suatu pipeline.

16. Apa perbedaan antara Continuous Integration, Continuous Delivery, dan Continuous Deployment?

- Continous Integration: menggabungkan perubahan kode ke *branch* main, di mana setiap perubahan tersebut akan divalidasi melalui pembuatan build dan serangkaian pengujian otomatis untuk menghindari konflik yang mungkin saja terjadi saat ingin melakukan integrasi ke *release branch*.

- Continous Delivery: mengotomatisasi pengiriman semua perubahan kode ke *production environment* setelah tahap *build* atau *continous integration* selesai dilakukan. 

- Continous Deployment: Praktek otomatisasi pengiriman perubahan kode ke lingkungan produksi setelah melewati CI/CD yang berarti setiap kali ada perubahan kode yang berhasil melewati serangkaian tes dan validasi, perubahan tersebut secara otomatis dirilis ke *production environment* tanpa adanya intervensi manusia. 

17. Apa perbedaan dari stages-stages yang berada dalam file .gitlab-ci.yml?
       
- Build: berkaitan dengan proses kompilasi dan *build* kode yang mungkin melibatkan proses build docker image jika aplikasi menggunakan Docker.

- Test: berkaitan dengan proses pengujian kualitas dan kontinuitas kode yang biasanya melibatkan *unit testing*, *integration testing*, dan *functional testing* untuk memastikan bahwa aplikasi memenuhi spesifikasi yang telah ditetapkan.

- Build-image: berkaitan dengan proses pembuatan Docker image.

- Publish-image: berkaitan dengan proses publikasi docker image dengan tag yang sesuai dengan *commit* yang telah dibuat ke registry atau server yang sesuai.

- Deploy: berkaitan dengan proses melakukan *deployment* aplikasi di server.     

18. Pada script gitlab-ci.yml, terdapat perubahan if: $CI_COMMIT_BRANCH == 'main' menjadi if: $CI_COMMIT_BRANCH == 'feat/tutorial-7-bacabaca'. Apa fungsi dari perubahan tersebut?

     Perubahan tersebut memungkinkan pengaturan alur kerja CI/CD pipeline agar spesifik sesuai dengan *branch* yang diinginkan, sehingga jobs atau stages yang ditentukan hanya akan dieksekusi jika commit terjadi pada branch 'feat/tutorial-7-bacabaca'. 

19. Apa yang dimaksud dengan "docker registry"? Apa fungsinya? 

       Docker registry adalah sistem penyimpanan sentral untuk menyimpan dan mengelola docker image. Docker registry berfungsi sebagai pusat penyimpanan yang terorganisir untuk menyimpan, berbagi, dan mendistribusikan Docker images dengan lebih efisien.

20. Dalam gitlab CI/CD, apa perbedaan antara: pipeline, stage, dan job?

- pipeline: entitas yang mengelola semua job dan stage dalam GitLab CI/CD dan berfungsi untuk mengkoordinasi dan menjalankan semua job dan stage sesuai dengan konfigurasi yang didefinisikan dalam file .gitlab-ci.yml. 

- stage: kelompok job yang dijalankan secara paralel dalam sebuah pipeline. Stage selanjutnya dapat dieksekusi hanya jika setiap job pada stage sebelumnya telah berhasil dijalankan. Stage memecah alur kerja CI/CD ke dalam beberapa fase, seperti build, test, publish, dan deploy.

- job: unit terkecil yang dieksekusi dalam GitLab CI/CD yang berfungsi untuk mengompilasi kode, menjalankan testing, atau melakukan deployment. Job memiliki *script* yang berisi *command* yang dijalankan secara berurutan. Setiap job dimiliki oleh sebuah *single stage*.

21. Sertakan screenshot fullscreen saat Anda mengakses apap-xxx.cs.ui.ac.id ketika sudah berhasil men-deploy aplikasi menggunakan CI/CD! 

Selama proses *deploy* aplikasi dengan menggunakan CI/CD, beberapa kali terjadi *failed* saat proses deploy pada jobs deploy dengan error seperti gambar di bawah ini. 

![failed1](https://media.discordapp.net/attachments/1151860115629150228/1176444367926480917/image.png?ex=656ee432&is=655c6f32&hm=9a6e9bc2871f9770b82dba40efabd3250eb5938fbe5e7420eef1508d3d8affcb&=&width=1317&height=655)

Saya mengasumsikan *error* ini terjadi karena adanya keterlambatan akses internet akibat banyak pengguna lain yang sedang mengakses server kirti (server kirti sedang mengalami *load* yang tinggi).

Namun, setelah melakukan *commit* dan *push* yang terakhir, proses *deploy* CI/CD langsung *failed* di jobs build dengan error sebagai berikut.

![failed2](https://media.discordapp.net/attachments/1151860115629150228/1176021616518500432/image.png?ex=656d5a7a&is=655ae57a&hm=db0a6a5f095f464c279548be15dcaff7923eb8cb5ece0840889f0ee26b68c558&=&width=1314&height=655)

Saya mengasumsikan error tersebut terjadi karena server Kirti di mana tempat GitLab Runner dijalankan mengalami kehabisan ruang pada perangkat penyimpanan.

22. Kapan proses CI/CD dijalankan di GitLab?

       Proses CI/CD akan dijalankan di Gitlab secara otomatis setiap kali terdapat pembaruan kode pada *repository* gitlab yang ditandai dengan adanya *commit* dan *push* baru yang dibuat.

23. Mengapa CI/CD ini penting? Apa manfaatnya?

       CI/CD penting karena dapat membantu membantu tim pengembangan, keamanan, dan operasi bekerja secara efisien dan efektif. Dengan CI/CD, setiap perubahan kode yang diusulkan diuji secara otomatis, sehingga masalah pada siklus pengembangan dapat lebih awal teridentifikasi. Dengan kata lain, CI/CD mempercepat pengiriman perangkat lunak, meningkatkan kualitas, dan mengurangi risiko kegagalan di lingkungan produksi. 

### What I did not understand
[x] 

## Tutorial 6

### What I Have Learned Today
* Mempelajari operasi-operasi penting pada Git beserta penerapannya.
* Mempelajari bagaimana membuat grafik yang dapat menggambarkan alur *commit*.
* Mempelajari bagaimana melakukan load testing dengan menggunakan JMeter dan performance testing dengan menggunakan JConsole.

1. Apa yang menjadi penyebab dari CONFLICT tersebut?

Jika diperhatikan, baik saat kita sedang berada di *branch* feat/tutorial-6-advancedgit-1 ataupun branch tut6-for-merge, terdapat penambahan konten html di *file* yang sama, yaitu *file* index.html dan di baris yang sama pada *file* tersebut, yaitu baris ke 5--7 dengan penambahan konten html yang berbeda untuk masing-masing *branch*. Oleh karena itu, konflik tersebut terjadi karena adanya percobaan penggabungan dua cabang *branch*, yaitu *branch* feat/tutorial-6-advancedgit-1 dengan tut6-for-merge, di mana pada kedua cabang tersebut terdapat perubahan yang berlawanan/bertentangan pada *file* index.html di baris yang sama pada kedua *branch*. 

2. Jelaskan perbedaan dari **"rebase –-continue"**, **"rebase –-skip"**, dan **"rebase –-abort"**!

- rebase --continue: perintah yang dapat digunakan untuk melanjutkan proses rebase setelah permasalahan (seperti konflik) telah berhasil diselesaikan.

- rebase --skip: perintah yang dapat digunakan untuk mengabaikan (skip) *commit* tertentu yang menyebabkan konflik atau permasalahan lainnya saat melakukan rebase. 

- rebase --abort: perintah yang dapat digunakan untuk membatalkan proses rebase sepenuhnya. Saat perintah ini dijalankan, semua perubahan yang dilakukan dalam proses rebase akan dibatalkan dan kita kembali ke kondisi sebelum rebase dimulai.

3. Apa perbedaan Git Merge dengan Git Rebase? Buatlah/carilah ilustrasi yang dapat menggambarkan perbedaanya!

Git Merge digunakan untuk menggabungkan perubahan dari dua *branch* menjadi satu dengan *commit* penggabungan baru, sedangkan git rebase digunakan untuk mengintegrasikan perubahan dari satu *branch* ke *branch* dasar yang baru dengan menerapkan ulang (*replay*) *commit* dari *branch* lama ke *branch* yang baru. 

**Git Merge**
![gitmerge](https://cdn.discordapp.com/attachments/1151860115629150228/1169695191238713425/image.png?ex=65565689&is=6543e189&hm=a9ab85df7ae3bb034f63bfefdfc7cc0a8541c2435b19610edb06e6d7c2a9fcfd&)

**Git Rebase**
![gitrebase](https://cdn.discordapp.com/attachments/1151860115629150228/1169694891576664104/image.png?ex=65565642&is=6543e142&hm=5122efb7a9d09a96dcb0aaf8f1efe130440dc5b2047a93e2aeaf3a37fdcac2fd&)

Sumber: https://phoenixnap.com/kb/git-rebase-vs-merge 

4. Mengapa hal pada langkah no 4 bisa terjadi? Mengapa git stash menjadi solusinya?

Hal tersebut terjadi karena pada kedua *branch*, baik feat/tutorial-6-advancedgit-1 atau feature-stash-1, masing-masingnya memiliki sebuah *file* dengan nama yang sama yaitu feature-stash.txt. Selain itu, saat masih berada pada *branch* feature-stash-1, kita melakukan penambahan konten/melakukan perubahan pada *file* feature-stash.txt, tetapi setelah melakukan perubahan tersebut kita tidak melakukan *commit* apa pun dan kita tetap mencoba untuk beralih ke *branch* feat/tutorial-6-advancedgit-1, sehingga menyebabkan terjadinya konflik. Dalam hal ini, git stash dapat menjadi solusinya, karena dengan menggunakan git stash, kita dapat menyimpan perubahan sementara yang belum di*commit* di *stashing area*, di mana *working directory* dan index akan dibersihkan, sehingga kita dapat beralih ke *branch* lain tanpa adanya konflik.

5. Sebutkan dan jelaskan tiga tipe dari Git Reset!

- git reset --soft: Perintah ini dapat digunakan saat kita ingin membatalkan *commit* tetapi tetap ingin mempertahankan perubahan pada *commit* tersebut sebagai *uncommited modifications*. Saat perintah ini dijalankan, HEAD *pointer* pada *commit* yang telah ditentukan akan di*reset* tanpa memberikan pengaruh/perubahan sama sekali terhadap index *file* (*staging area*) atau *working tree*. Dalam kata lain, git reset --soft hanya melakukan pemindahan HEAD *reference* ke commit yang berbeda, di mana setiap perubahan yang ada akan tetap ada pada index *file* dan hanya akan dianggap sebagai "Changes to be committed" pada git status. 

- git reset --hard: Perintah ini akan mengembalikan *repository* ke keadaan yang sesuai dengan *commit* yang telah ditentukan, sehingga semua perubahan yang dilakukan setelah *commit* tersebut akan **dihapus secara permanen**. Saat perintah ini dijalankan, semua perubahan yang ada pada index *file* (*staging area*) akan dihapus dan *file-file* yang telah diubah akan dikembalikan ke keadaan pada *commit* yang ditentukan. 

- git reset --keep:  Perintah ini digunakan untuk menjaga perubahan lokal yang belum di*commit* agar tetap ada di *working tree*. Saat perintah ini dijalankan, index *file* (*staging area*) akan di*reset* dan *file-file* pada *working tree* akan diubah sesuai dengan isi *commit* apabila terdapat perbedaan antara isi *commit* yang telah ditentukan oleh HEAD dan HEAD. Namun, apabila *file* yang berbeda antara *commit* dan HEAD tersebut memiliki perubahan lokal yang belum di*commit*, maka perintah ini akan dibatalkan dan tidak menghasilkan perubahan apa pun.

6. Apa itu git revert? Apa perbedaannya dengan git reset?

git revert adalah perintah yang dapat digunakan untuk menghapus perubahan yang ada dalam satu atau beberapa *commit* tertentu tanpa mengubah riwayat *commit* yang ada. Perintah ini akan membuat *commit* baru yang membatalkan perubahan yang ingin di-"revert", di mana *commit*  baru tersebut akan ditambahkan ke riwayat *commit* yang berisi perubahan pembatalan dari *commit* yang telah ditentukan. Berbeda dengan git revert, git reset digunakan untuk mengembalikan *file* yang telah di*staging* (atau yang ada di index) ke *working directory*, sehingga perubahan yang telah di*staging* tersebut tidak akan dimasukkan dalam *commit* berikutnya. Saat perintah git reset dijalankan, riwayat *commit* akan berubah, karena terjadi pemindahan HEAD pointer ke commit yang telah ditentukan. Selain itu, perintah git reset hanya berlaku di dalam *repository* lokal, sehingga perubahan yang direset hanya akan berpengaruh di *repository* lokal saja, tidak seperti git revert yang berlaku di dalam *remote repository*.

7. Buatlah grafik yang menggambarkan alur commit pada bagian Git Flow and Branching ini serta jelaskan! Grafik dapat berupa tulis tangan maupun menggunakan software.

![gambar](https://media.discordapp.net/attachments/1151860115629150228/1169881454537359420/5vNnmZ7Zp8gAAAAASUVORK5CYII.png?ex=65570402&is=65448f02&hm=5f553178763a2bc07df9c3e3cc088dea338ddc39050bccb39a29a9942582c44e&=&width=1440&height=564)

Berikut adalah penjelasan masing-masing node yang terdapat pada graf:

- **node c**: Node ini menandakan bahwa pada *branch* main telah dibuat *branch* baru, yaitu *branch* development dan terjadi perpindahan *branch* dari *branch* main ke *branch* development.

- **node e**: Node ini menandakan bahwa terdapat add dan commit baru untuk penambahan direktori dan file baru, yaitu index.html di branch development.

- **node g**: Node ini menandakan bahwa pada *branch* development telah dibuat *branch* baru, yaitu *branch* feature-a dan terjadi perpindahan *branch* dari *branch* development ke *branch* feature-a.

- **node i**: Node ini menandakan bahwa terdapat add dan commit baru untuk menambahkan perubahan pada *file* base.html di branch development.

- **node j**: Node menandakan bahwa telah terjadi MR antara *branch* feature-a dan *branch* development.

- **node m**: Node ini menandakan bahwa terdapat add dan commit baru untuk menambahkan perubahan pada *file* base.html di branch feature-b.

- **node k**: Sebelumnya, node ini merupakan branch baru yang dibuat dari branch development (terusan dari node e), namun setelah selesai mengerjakan feature-b (eksekusi dari node k dan m) dan dilakukan pengecekan pada repository Git Lab, sudah ada perubahan terbaru pada branch development, sehingga dilakukan rebase dan feature-b akan melanjutkan commit terbaru yang sebelumnya terjadi di branch development (node k dan node m akan meneruskan node j).

- **node q**: Node ini menandakan bahwa telah terjadi add dan commit pada branch feature-b.

- **node s**: Node menandakan bahwa telah terjadi MR antara *branch* feature-b dan *branch* development.

8. Apa kegunaan dari langkah di atas?

Langkah-langkah di atas merupakan pengaturan awal yang dapat dilakukan untuk membuat *test plan* di JMeter dan berguna untuk mengidentifikasi *user* agar *test plan* lebih terorganisir (langkah 1), *test plan* dapat diidentifikasi dengan jelas (langkah 2), membuat Thread Group yang nantinya dapat digunakan untuk menetukan berapa banyak *threads* yang akan berpartisipasi (langkah 3), dan mengatur *headers* HTTP yang akan digunakan dalam pengujian (langkah 4).

9. Apa itu JSON Extractor? Sebutkan semua kegunaannya di Test Plan ini!

JSON Extractor adalah alat yang dapat digunakan untuk membantu mengeksekusi ekspresi JSON Path terhadap respons JSON yang kemudian hasilnya akan disimpan ke dalam variabel JMeter. JSON Extractor berguna untuk mengotomatiskan pengujian API atau layanan web yang menghasilkan respons dalam format JSON. Pada Test Plan ini, JSON Extractor berguna untuk meng*generate* id buku, mengekstrak nilai id buku tersebut dari respons JSON yang dihasilkan, dan menyimpan nilai id buku tersebut ke dalam variabel JMeter yang kemudian nilai tersebut dapat digunakan dalam skenario test.

10. Apa itu Assertions dalam JMeter? Sebutkan contoh 3 Assertions dan kegunaannya!

       Assertions adalah elemen dari *test plan* pada JMeter yang digunakan untuk memeriksa, memvalidasi, dan memastikan bahwa nilai respons yang diterima dari server sesuai dengan kriteria yang diharapkan.

       Contoh Assertion:
       - Response Assertion: digunakan untuk memverifikasi apakah respons server mengandung string *pattern* yang telah ditentukan sebelumnya.
       - Duration Assertion: digunakan untuk menguji apakah setiap respons dari server diterima dalam rentang waktu tertentu yang telah ditentukan. 
       - Size assertion: digunakan untuk menguji apakah setiap respons dari server mengandung jumlah byte yang sesuai dengan yang diharapkan (misalnya kita dapat menentukan apakah ukuran respons harus sama dengan, lebih besar dari, lebih kecil dari, atau tidak sama dengan jumlah byte yang telah ditentukan).

11. Apa itu Number of Threads dan Ramp-up Period? Apa hubungan antar keduanya?

       - Number of Threads: mengacu pada jumlah pengguna virtual atau koneksi yang akan berpartisipasi dan disimulasikan pada *test plan*.
       - Ramp up period: total durasi yang dibutuhkan untuk menjalankan seluruh skenario pengujian dari awal hingga akhir.

       Hubungan antara keduanya adalah bahwa Ramp-up Period mengatur bagaimana pengguna virtual akan ditambahkan secara bertahap selama durasi tertentu hingga mencapai jumlah maksimum yang ditentukan dalam Number of Threads.

12. Gunakan angka 1000 untuk Number of Threads dan 100 untuk Ramp-up period. Jalankan Test Plan dengan konfigurasi tersebut. Kemudian, perhatikan Summary Report, View Result Tree, Graph Result, dan Assertion Result. Buatlah penjelasan minimal 2 paragraf untuk menjelaskan temuan menarik kalian terhadap hasil-hasil tersebut. Sertakan screenshot dari keempat result tersebut. Sertakan juga info mengenai prosesor, RAM, dan penggunaan hardisk HDD atau SSD dari perangkat Anda. (Jika perangkat Anda tidak kuat dengan angka konfigurasi tersebut, silakan turunkan angkanya.)

       **Summary Report**
       ![gambar](https://media.discordapp.net/attachments/1151860115629150228/1169644232819081308/image.png?ex=65562714&is=6543b214&hm=51c1a6e865559983687410e3c7885f20dd3e3a6cbe6e20c7c8c5e3cff4d52fc5&=&width=1057&height=595)

       **View Result Tree**
       ![gambar](https://media.discordapp.net/attachments/1151860115629150228/1169644422443585587/image.png?ex=65562741&is=6543b241&hm=e08cfd30d4ecff2cdceac0034213c9d4da88435fada513df14fe7b37982107d2&=&width=1057&height=595) 

       **Graph Result**
       ![gambar](https://media.discordapp.net/attachments/1151860115629150228/1169644485005819925/image.png?ex=65562750&is=6543b250&hm=17e69c5f5ece948a873b08e4142f3d78e215d6d3ab7e75e96c456bd9fe8c500a&=&width=1057&height=595)

       **Assertion Result**
       ![gambar](https://media.discordapp.net/attachments/1151860115629150228/1169644556346728518/image.png?ex=65562761&is=6543b261&hm=ca75ac32f41235396bd2e26afda5938bf67f865cfc3b116954470be76d915dd1&=&width=1057&height=595) 

       Dari hasil yang ditunjukkan pada gambar di atas, terlihat bahwa test plan berlangsung selama 7 menit 23 detik. Pada summary report, terlihat bahwa tingkat total error yang dihasilkan dari kelima HTTP Request yang diuji masih tergolong sangat tinggi, yaitu sebesar 74.16%, dengan error tertinggi dihasilkan oleh http request Search Buku By Judul, yaitu sebesar 95.60% dengan throughput sebesar 2.3/sec. Menurut asumsi saya, salah satu pemicu tingginya tingkat error tersebut dapat disebabkan oleh jumlah number of threads dan ramp-up period yang masih terlalu tinggi dan terlalu cepat untuk diterapkan pada perangkat yang saya gunakan, walaupun tidak terjadi lag sama sekali pada perangkat selama test plan berlangsung. 
       
       Meskipun memiliki tingkat error tinggi, jika dilihat dari Graph Result, nilai *average*, *median*, *deviation*, dan *throughput* untuk kelima http request terlihat cenderung stabil. Pada view result tree, dapat terlihat judul http request apa saja yang mengalami error dan yang tidak selama test plan berlangsung. Selain itu, selama test plan berlangsung, saya juga memerhatikan bahwa Random Request baru muncul pada summary report, view result tree, dan assertion result di saat threads yang aktif selama pengujian mulai mengalami penurunan. 

       **processor**: AMD Ryzen 5 5500U

       **RAM**: 8GB

       **harddisk**: SSD

13. Sembari menjalankan Test Plan, perhatikan pergerakan grafik pada JConsole. Buatlah penjelasan minimal 2 paragraf untuk menjelaskan temuan menarik kalian terhadap hasil-hasil tersebut. Sertakan screenshot dari grafik-grafik tersebut.

       ![gambar](https://media.discordapp.net/attachments/1151860115629150228/1169698877478285352/image.png?ex=655659f8&is=6543e4f8&hm=5e65a9669c49f8730c371d6f29bb336cc84b9efc113a7085f49243b5adc4120d&=&width=987&height=655)

       Lonjakan heap pada memory usage yang dapat dilihat pada grafik Heap Memory Usage dan penggunaan CPU yang dapat dilihat pada grafik CPU Usage dalam rentang waktu yang sama terjadi beriringan dengan peningkatan error pada request di JMeter. Hal tersebut menunjukkan bahwa aplikasi mungkin mengalami tekanan yang melebihi kapasitasnya. Lonjakan heap memory usage dan penggunaan CPU biasanya terkait dengan peningkatan beban kerja atau aktivitas aplikasi yang lebih intens. 

       Selain itu, grafik threads yang awalnya mengalami peningkatan yang tinggi dan kemudian stabil serta turun saat pengujian selesai, menandakan bahwa selama pengujian berlangsung, terdapat peningkatan number of threads atau thread yang aktif. Ketika pengujian selesai, thread aktif pun akan semakin berkurang yang ditandai dengan penurunan yang drastis pada grafik threads. Pada grafik classes juga dapat dilihat bahwa grafik cenderung stagnan dari awal hingga akhir pengujian, lalu turun saat tidak ada lagi yang diload, ini menandakan bahwa selama pengujian, tidak ada penambahan kelas baru yang diperlukan.

14. Apa itu Load Testing? Buatlah kesimpulan dari pengerjaan tutorial JMeter & JConsole ini.

       Load testing adalah pengujian yang dilakukan untuk mengetahui bagaimana perilaku sistem saat menghadapi load yang normal atau load yang tinggi. Melalui load testing, kita dapat menentukan apakah suatu sistem dapat menghadapi dan mengatasi beban yang tinggi saat terdapat permintaan yang tinggi atau tidak.

       Kesimpulan:

       Pada tutorial ini, telah dilakukan *load testing* pada *project* bacabaca dengan memanfaatkan JMeter dan *performance testing* dengan memanfaatkan JConsole. Kedua jenis pengujian ini dijalankan secara bersamaan untuk mengukur bagaimana aplikasi "bacabaca" berkinerja saat menghadapi beban tinggi dan untuk memantau aspek-aspek kinerja sistem. Kedua proses ini, diawali dengan pembuatan *test plan* pada JMeter yang di dalamnya terdapat pengaturan mengenai thread group, http manager, http request, hingga listener yang digunakan, di mana selama proses load testing dilakukan, kita juga dapat memantau performance testing melalui JConsole yang dapat dilihat dari grafik CPU Usage, Heap Memory Usage, Classes, dan Threads yang dihasilkan. Selain itu, dalam melakukan load testing pada JMeter, sangat penting untuk memerhatikan pengaturan jumlah thread (number of threads) dan ramp-up period yang akan memengaruhi seberapa baik aplikasi dapat menangani beban yang tinggi.

### What I did not understand
[x] 

## Tutorial 5

### What I Have Learned Today

Membuat dan mengakses *webservice*, menggunakan *web service* dengan menggunakan *library* *Web Client*.

1. Apa itu Postman? Apa kegunaannya?

Postman adalah *platform* yang menyediakan berbagai fitur yang berguna dalam proses pengembangan dan pengujian APIs. Dengan menggunakan Postman, dapat memudahkan kita dalam menyederhanakan kolaborasi dan memudahkan setiap tahap dalam siklus API, sehingga API dapat dibuat dengan lebih baik dan cepat.

2. Apa yang terjadi ketika kita tidak menggunakan @JsonIgnoreProperties dan @JsonProperty pada model Buku dan Penulis? apabila terjadi error, mengapa hal tersebut dapat terjadi?

Jika kedua anotasi tersebut tidak disertakan, maka semua properti pada model Buku dan Penulis akan secara otomatis disertakan ke dalam objek yang dihasilkan, sehingga akan terjadi error ketika terjadi ketidakcocokan antara nama properti dan tipe data antara model dan properti dalam objek JSON dan tidak ditemukannya properti yang diperlukan dalam objek JSON.

3. Pada tutorial ini, kita mencoba untuk memanggil data dengan menggunakan method GET. Namun, apakah kita dapat memanggil data dengan method lainya, seperti POST? Jelaskan pendapat kalian?

Dalam melakukan pengambilan data, kita dapat menggunakan method lainnya seperti POST hanya jika:
- server atau *external service* yang digunakan mendukung method HTTP yang ingin digunakan. Jika server hanya mengizinkan operasi GET, maka Anda tidak akan dapat menggunakan metode POST atau yang lainnya.
- Tujuan dari operasi yang ingin dilakukan. Misalnya, GET digunakan untuk mengambil data tanpa mengubahnya, sementara POST digunakan untuk mengirim data yang kemungkinan akan mengubah data di server.

4. Selain method GET dan POST, sebutkan dan jelaskan secara singkat HTTP request methods lainnya yang dapat kita gunakan!

- PUT: Digunakan untuk memodifikasi data yang terdapat pada server. Bersifat idempoten yang berarti jika request PUT berkali-kali dikirimkan, hasilnya akan tetap sama.
- PATCH: Digunakan untuk memperbarui bagian-bagian tertentu pada data (*partial updates*). Tidak bersifat idempoten dan sangat berguna jika kita ingin mengubah *field* tertentu tenpa memberikan efek kepada *fields* yang lainnya pada data.
- DELETE: Digunakan untuk menghapus data di lokasi tertentu pada server dan tidak bersifat idempoten.

5. Apa kegunaan atribut WebClient?

Atribut WebClient biasa digunakan untuk membuat klien HTTP *non-block* dalam *environment* WebFlux. Dengan menggunakan WebClient, dapat memudahkan dalam melakukan interaksi dengan *web service* atau API eksternal dengan melakukan HTTP request, seperti GET, POST, PUT, dan DELETE. Selain itu, WebClient juga dapat digunakan untuk mengambil dan mengirim data dari layanan eksternal, mengakses *resources* REST, *handling error*, testing, dan mengintegrasikan reactive programming dengan menggunakan Mono dan Flux dari reactor.

### What I did not understand
[x] 

## Tutorial 4

### What I Have Learned Today
Pada lab ini, saya mempelajari tentang penggunaan *static files* untuk membuat *interface* dan penggunaan *spring profiles* untuk membedakan konfigurasi pada setiap *environment* yang berbeda pada tiap tahap *deployment* pengembangan perangkat lunak

1. Pada file html project bacabaca, terdapat baris kode berikut.

Apa itu xmlns? Jawab dengan singkat dan padat.

xml namespace (xmlns) adalah sekumpulan *names* yang diidentifikasi oleh URI *reference* dan biasa digunakan untuk mendefinisikan *namespace* jika diperlukan.

2. Jelaskan perbedaan th:include dan th:replace! Jawab dengan singkat dan padat.

- `th:include`: memasukkan konten dari file template ke dalam elemen target, sementara elemen target tetap ada dengan konten tambahan yang di-include.
- `th:replace`: menggantikan seluruh tag yang digunakan (tag host) dengan konten dari fragmen atau template yang ditentukan, sehingga seluruh tag host akan digantikan dengan konten yang di-include.

3. Kapan sebaiknya kita menggunakan static files dibandingkan dengan file eksternal menggunakan link? Jawab dengan singkat dan padat.

Penggunaan static files atau file eksternal tergantung pada kebutuhan konten dan kinerja website yang ingin dibangun. Static files lebih baik digunakan apabila konten pada *website* seperti gambar, CSS, JavaScript, dan file tidak sering dilakukan perubahan. Selain itu, static files juga dapat digunakan ketika kita ingin meningkatkan kinerja dan kecepatan dari website.

4. Jelaskan caramu menyelesaikan latihan no 2.

- Menambahkan dua *method* baru di *file* `BukuController.java`, yaitu *method* `addRowPenulisBukuUpdate` yang digunakan untuk menambahkan baris baru ke dalam form penulis buku dan `deleteRowPenulisBukuUpdate` yang digunakan untuk menghapus baris penulis tertentu.
- Pada *method* `addRowPenulisBukuUpdate`, akan diperiksa apakah sudah ada `listPenulis` dalam objek `updateBukuRequestDTO` atau belum. Jika belum ada, objek `Penulis` akan ditambahkan ke dalam list untuk dirender sebagai row baru. Saya juga menambahkan kode  `model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());` untuk mengirimkan daftar penulis yang sudah ada yang nantinya akan dijadikan pilihan pada *dropdown*
- Pada *method* `deleteRowPenulisBukuUpdate`, saya menambahkan kode `updateBukuRequestDTO.getListPenulis().remove(row);` untuk menghapus baris penulis sesuai dengan indeks yang diberikan oleh parameter row.
- Saya juga menambahkan tabel untuk menampilkan form yang berisi penulis buku yang sudah ada sebelumnya (*fulfilled form*) dengan menggunakan iterasi `th:each` pada `listPenulis` dalam objek updateBukuRequestDTO dan *dropdown* untuk memilih penulis dari daftar penulis yang tersedia (`listPenulisExisting`) pada *file* `form-update-buku.html`. Selain itu, saya juga menambahkan tombol `Tambah Row` untuk menambahkan baris baru ke form dan `Hapus` untuk setiap baris penulis dengan indeks yang sesuai.

5. Jelaskan apa itu pagination! Jawab dengan singkat dan padat.

Pagination adalah teknik pembagian konten menjadi beberapa halaman terpisah yang bertujuan untuk mempermudah navigasi pengguna.

6. Sebutkan salah satu skenario yang mengharuskan adanya perbedaan dev dan prod dan jelaskan alasannya!

Skenario yang mengharuskan adanya perbedaan dev dan prod adalah terkait *data configuration*. Di *environment* *development*, umumnya digunakan basis data lokal atau basis data dummy dengan tujuan untuk proses pengujian dan pengembangan yang memungkinkan pengembang bekerja dengan data yang realistis tanpa berdampak pada basis data produksi yang sebenarnya. Di sisi lain, di *environment* prod, konfigurasi basis data harus diatur untuk menggunakan basis data produksi yang sesungguhnya, biasanya berada di server basis data yang terpisah. Pemisahan tersebut dilakukan dengan tujuan untuk memastikan keamanan, kinerja, dan keandalan sistem produksi, karena sistem tersebut menangani data pengguna yang sebenarnya dan melayani lalu lintas langsung dari pengguna. Dengan memisahkan konfigurasi basis data antara dev dan prod, pengembang dapat bekerja dengan efisien dan aman di lingkungan pengembangan, sementara lingkungan produksi tetap stabil dan dapat diandalkan bagi *end-user*.

7. Lampirkan screenshot kalau kamu sudah berhasil membuat user untuk environment production serta bukti bahwa kamu sudah berhasil mengakses database production dengan user tersebut!

![gambar](https://cdn.discordapp.com/attachments/1151860115629150228/1156885388854566922/image.png?ex=651698fa&is=6515477a&hm=7fa97377632b795eea6b801078729c56419461956d4f1be6758d5d2414ba409d&)

### What I did not understand
- [x] Pembuatan custom error untuk *environment* prod.

## Tutorial 3

### What I Have Learned Today
Penggunaan JPA dalam pemrograman java menggunakan pendekatan *Object Relational Mapping* (ORM).

1. Jelaskan apa itu ORM pada spring serta apa fungsi dan kegunaanya?

*Object Relational Mapping* (ORM) adalah teknik pemetaan objek relasional yang digunakan untuk mengakses server database. Teknik tersebut berfungsi untuk menghubungkan dan mensimplifikasi hubungan antara struktur data dalam basis data relasional dengan kode *Object Oriented Programming* (OOP). Kegunaan dari ORM di antaranya, yaitu:
- Memudahkan dalam melakukan pemetaan objek relasional dan abstraksi *database*, sehingga memudahkan *developer* dalam menggunakan, mengakses, menyimpan, dan memanipulasi data yang terdapat pada *database* tanpa harus menulis SQL Query secara manual.
- Memudahkan *developer* dalam melakukan pembaruan pemetaan objek secara otomatis yang dapat meminimalisir terjadinya *human-error*.

2. Jelaskan secara singkat apa itu dan kegunaan dari tag-tag dibawah ini. 

`(@Entity, @Table, @Column)`
- `@Entity`: Digunakan untuk memberitahu JPA bahwa kelas tersebut merupakan entitas yang harus dikelola JPA. Nantinya, JPA akan membuat pemetaan antara entitas dan tabel dalam *database*.
- `@Table`: Digunakan untuk memberitahu program bagaimana dan dimana data dari objek akan disimpan di *database*.
- `@Column`: Digunakan untuk menentukan nama kolom yang akan digunakan dalam tabel di *database*.

3. Pada relasi buku ke penulis, terdapat tag 
@JoinTable(name = "penulis_buku", joinColumns = @JoinColumn(name = "id"),
       inverseJoinColumns = @JoinColumn(name = "id_penulis"))
Jelaskan maksud dari tag @JoinTable tersebut beserta parameternya (name, joinColumns, inverseJoinColumns) dan implementasinya pada database.

- `@JoinTable` merupakan anotasi JPA yang digunakan untuk mengkustomisasi atau menentukan *join table* antara dua entitas dalam *many-to-many relationship*.
- `name` digunakan menentukan nama tabel gabungan yang akan dibuat pada *database*.
- `joinColumns` digunakan untuk menentukan kolom yang akan menjadi *foreign key* dalam tabel gabungan yang mengarah ke entitas saat ini.
- `inverseJoinColumns` digunakan untuk digunakan untuk menentukan kolom yang akan menjadi *foreign key* dalam tabel gabungan yang mengarah ke tabel lain dalam *many-to-many relationship*.

Implementasi pada *database* akan menghasilkan tabel bernama `penulis_buku`yang memiliki *primary key* yaitu id. Kemudian, atribut `@JoinColumn` akan menghubungkan entitas Penulis dengan menjadikan `id_penulis` sebagai *foreign key* dari tabel `penulis_buku`.

4. Bagaimana cara kerja dari dependensi java mapper, yaitu mapstruct?
- Setelah anotasi `@Mapper` ditambahkan pada *interface* dan metode abstraknya telah didefinisikan, `@Mapper` akan melakukan pemetaan dengan objek-objek yang terkait yang sesuai dengan objek tujuan (target) berdasarkan kustomisasi pemetaan yang bisa kita lakukan dengan anotasi `@Mapping`. Jika metode pemetaan yang dihasilkan oleh MapStruct dipanggil, MapStruct akan melakukan pemetaan objek tujuan berdasarkan pemetaan yang telah didefinisikan sebelumnya. MapStruct akan secara otomatis menganalisis atribut-atribut di dalam objek dan mencocokkannya berdasarkan nama dan tipe data atribut-atribut yang sesuai. Jika terdapat perbedaan antara tipe data atribut pada objek sumber dan objek tujuan, MapStruct akan mencoba mengonversi tipe data tersebut. Contohnya, jika objek sumber memiliki atribut dengan tipe String, dan objek tujuan memiliki atribut dengan tipe Integer, MapStruct akan mencoba mengonversi nilai String ke Integer jika konversi tersebut valid.

5. Apa keuntungan dari implementasi soft delete?

- Jika terjadi kesalahan penghapusan data atau ingin memulihkan data yang telah dihapus, data bisa dipulihkan kembali, karena data yang dihapus menggunakan *soft delete* tetap masih ada di dalam *database*, karena data hanya akan ditandai sebagai "dihapus".
- Pelacakan terkait riwayat penghapusan data, seperti kapan data dihapus dapat dilakukan dengan lebih mudah.

### What I did not understand
- [x] Belum terlalu memahami cara kerja dari *mapping*.

## Tutorial 2
### What I Have Learned Today
Konsep model, service, dan controller beserta alur proses dan juga fungsinya.

1. Apa itu DTO? Jelaskan kegunaannya pada proyek ini?

*Data Transfer Object* (DTO) adalah objek yang digunakan untuk mengirimkan data antara proses atau komponen dalam aplikasi untuk mengurangi jumlah *method* yang dapat dipanggil. Data yang berasal dari berbagai objek pada satu *layer* akan dikemas menjadi satu objek tunggal agar transfer proses atau komponen dalam aplikasi dapat berjalan dengan lebih mudah dan efisien. Kegunaannya dalam proyek ini adalah untuk mengemas atribut-atribut yang terdapat pada Buku ke dalam objek BukuDTO yang nantinya data yang telah dikemas tersebut akan ditampilkan kepada pengguna melalui tampilan *web* dalam bentuk informasi dan *Update* data terkait buku melalui HTML.

2. Apa itu UUID? Mengapa UUID digunakan?

*Universally Unique IDentifier* (UUID) adalah sebuah rangkaian alfanumerik sepanjang 36 karakter yang dihasilkan dan dimanipulasi oleh sistem yang bersifat unik secara global. Kemampuannya dalam mengidentifikasi objek atau entitas secara unik, bersifat independen, dan kompatibel terhadap banyak bahasa pemrograman, membuat UUID banyak digunakan dalam berbagai aplikasi dan sistem.

3. Pada *service*, mengapa perlu ada pemisahan antara *interface*, dan implementasinya?

Pemisahan antara *service* dan *interface* perlu dilakukan, karena dapat:
- memudahkan *developer* dalam melakukan perubahan implementasi *service* tanpa harus mengubah/mengganggu komponen lainnya yang sudah ada dan sudah digunakan.
- Pemisahan antara *service* dan *interface* juga meningkatkan keterbacaan kode bagi developer.
- memisahkan konsep antara kontrak (apa yang perlu dilakukan) dan implementasinya.

4. Menurut kamu anotasi @Autowired pada class Controller tersebut merupakan implementasi dari konsep apa? Dan jelaskan secara singkat cara kerja @Autowired tersebut dalam konteks service dan controller yang telah kamu buat.

@Autowired pada class Controller merupakan implementasi dari konsep *Dependency Injection* yang dapat memungkinkan Spring container  untuk menyediakan *instance* dari dependensi yang diperlukan ketika komponen dibuat.
**Cara kerja**:
Setelah objek dibuat pada service, Spring container akan mencari objek di service yang bersesuaian dengan kriteria dari objek yang telah ditentukan oleh @Autowired. Setelah objek ditemukan, objek service tersebut akan diinjeksikan ke dalam objek controller oleh Spring container, lalu objek controller tersebut dapat mengakses fungsionalitas dari service menggunakan objek service.

5. Apa perbedaan @GetMapping dan @PostMapping?

@GetMapping:
- Berkaitan dengan *handler methods* yang akan dijalankan ketika terdapat permintaan HTTP GET.
- Berkaitan dengan pengambilan atau pengaksesan informasi, seperti menampilkan halaman web, membaca data dari database, atau mengambilan informasi tanpa melakukan perubahan data yang signifikan.

@PostMapping:
- Berkaitan dengan *handler methods* yang akan dijalankan ketika terdapat permintaan HTTP POST.
- Berkaitan dengan perubahan data di server, seperti penambahan data baru, perbaruan data, atau operasilainnya yang dapat menyebabkan perubahan data pada sistem.

6. Jelaskan proses yang terjadi di controller, model, dan service pada proses create buku, mulai dari fungsi formAddBuku hingga pengguna menerima halaman success-create-buku.

- Saat pengguna mengakses halaman tambah buku atau buku/create, *request* akan ditangani oleh *method* FormAddBuku yang terdapat pada Controller. Kemudian akan dibuat DTO yang akan dikembalikan melalui model pada halaman form-create-buku.html. 
- Setelah pengguna mengisi *form* dan menekan tombol *submit*, *request* POST sudah diterima oleh Controller dan fungsi addBuku akan dijalankan.
- Setelah fungsi addBuku dijalankan, data yang didapatkan pada DTO akan digunakan untuk *instance* baru yang terdapat pada class Buku dan *method* addBuku pun akan dipanggil.
- *Instance* yang terdapat pada Buku tersebut akan diterima oleh Controller, lalu service akan menambahkan *instance* tersebut ke dalam listBuku.
- Kemudian, Controller akan menambahkan atribut id dan judul buku pada model halaman success-create-buku, sehingga id dan judul dari buku tersebut dapat diakses melalui *file* HTML.

7. Jelaskan mengenai th:object!

th:object merupakan sebuah atribut dalam Thymeleaf yang digunakan untuk mengumpulkan data dengan cara mengaitkan objek Java dengan elemen *form* pada halaman *web*. 

8. Jelaskan mengenai th:field!

TIPS: Buka “view page source” dari halaman Tambah Buku di browser. Lakukan screenshot kemudian jelaskan temuan kalian.
th:field merupakan sebuah atribut dalam Thymeleaf yang digunakan untuk mengikat dan memproses data antara objek dalam model dengan elemen HTML pada *form*. Dengan menggunakan th:field, *field* dapat ditentukan dari model *object* yang akan di*bind* ke *input HTML.

![ss](https://cdn.discordapp.com/attachments/1151860115629150228/1151860302154043432/image.png)

*screenshot* di atas ditemukan bahwa pada HTML untuk halaman Tambah Buku, atribut th:object sudah tidak terlihat dan th:field sudah berubah menjadi id dan name pada *form* HTML.

### What I did not understand
- [x] Masih belum terlalu paham terkait konsep DTO

## Tutorial 1
### What I have learned today
Melakukan *setup* Spring Boot menggunakan Maven
*Review* operasi Git yang pernah dipelajari sebelumnya.

### GitLab
1. Apa itu *Issue Tracker?* Apa saja masalah yang dapat diselesaikan dengan *Issue Tracker*?
*Issue tracker* merupakan sebuah tool atau sistem yang dapat digunakan oleh individu atau *team* untuk mengelola dan  melacak *bugs*, *tasks*, *issues*, *feature requests*, *change* **request*, *process workflows*, dan hal lainnya yang berkaitan dengan *product development*. Masalah-masalah yang dapat diselesaikan dengan *issue tracker* di antaranya adalah:
- Pelacakan *bugs* dan *issues*
- *Change tracking*
- Pengelolaan saat pengintegrasian kode (*pull request*)

2. Apa perbedaan dari git merge dan git merge --squash?

Git Merge:
- Detail dari setiap *commit* individual dari setiap branch yang terjadi sebelum merging tetap dapat dilihat di *history* git dan diurutkan berdasarkan waktu dilakukannya *commit*.
- Ketika perintah git merge dijalankan, perubahan dari setiap *branch* akan digabungkan menjadi sebuah *commit merge* yang baru.

Git merge --squash:
- Detail dari setiap *commit* individual dari setiap branch yang terjadi sebelum proses merging (perintah merge --squash dijalankan) tidak dapat dilihat kembali di *history* git.
- Setiap perubahan akan digabungkan menjadi satu *commit* tunggal yang mewakili perubahan yang terjadi, sehingga pada *history* git nantinya, kita hanya dapat melihat satu *commit* saja yang berisi semua perubahan.

3. Apa keunggulan menggunakan Version Control System seperti Git dalam pengembangan suatu aplikasi?
- Memungkinan developer untuk bekerja secara bersamaan pada komponen yang berbeda, seperti pembuatan branch untuk masing-masing fitur, sehingga developer bisa lebih fokus pada fitur tertentu yang sedang dikembangkannya tanpa mengganggu kode satu sama lain.
- Branching dan Merging dapat dilakukan dengan lebih cepat, fleksibel, dan efisien. 
- Terhubungnya Git dengan CI/CD Pipelines, memungkinkan perilisan aplikasi dapat dilakukan dengan lebih cepat dan stabil.
- Banyaknya dokumentasi yang tersedia membuat pengembang dapat memahami penggunaan git dengan lebih baik.

### Spring
4. Apa itu library & dependency?
Library adalah sekumpulan kode atau modul yang menyediakan fungsionalitas tertentu yang dapat digunakan dalam proses pengembangan sebuah aplikasi. Library memungkinkan kita untuk melakukan tugas-tugas tertentu tanpa harus menulis ulang kode, karena library pada Spring berisi berbagai kelas, metode, dan komponen yang dapat langsung digunakan.

Dependency merujuk kepada komponen/paket/modul/libraries yang menyediakan fungsionalitas tambahan yang dibutuhkan oleh aplikasi, biasanya digunakan dalam suatu aplikasi yang bergantung pada dukungan dari aplikasi tersebut.

5. Apa itu Maven? Mengapa kita menggunakan Maven? Apakah ada alternatif dari Maven?
Apa itu Maven?
Maven adalah sebuah software dan tool komprehensif yang sebagian besar digunakan dalam proyek-proyek berbasis Java, di mana software tersebut juga menyediakan cara yang terstruktur dan efisien dalam membangun aplikasi serta mengelola project dependencies dan application package. 

Mengapa kita menggunakan Maven?
Kemampuannya dalam mengelola dependencies, build process, dan project configuration secara otomatis,  membuat kita dapat lebih mudah dalam mengelola semua proses, meningkatkan performa dari building process pada project, serta melakukan kolaborasi.

Apakah ada alternatif dari Maven?
Ada. Alternatif lainnya selain Maven adalah Gradle, Ant, SBT, Bazel, dan Buck.

6. Jelaskan bagaimana alur ketika kita menjalankan http://localhost:8080/celsius-converter/90 sampai dengan muncul keluarannya di browser!
- Browser akan mengirimkan HTTP GET Request ke server localhost:8080 dengan 90 sebegai path variable.
- Server menerima request dan memetakan request tersebut ke method controller. Perintah `2GetMapping("/celsius-converter/{celsius}")` akan bekerja.
- Nilai yang terdapat pada URL path akan dikalkulasikan dan dikonversi ke skala lainnya
- Controller method akan mengembalikan nama view yang akan dirender
- Server memproses request dan mengirimkan view tersebut kembali ke browser sebagai HTTP response
- User dapat melihat nilai yang telah dikonversi.

7. Selain untuk pengembangan web, apa saja yang bisa dikembangkan dengan Spring framework?
- Internet of Things (IoT)
- Aplikasi Mobile
- Cloud-native application
- Batch processing
- Standalone application

8. Apa perbedaan dari @RequestParam dan @PathVariable? Kapan sebaiknya menggunakan @RequestParam atau @PathVariable?

@RequestParam
- Digunakan untuk mengekstrak parameter query dari request URL
- Biasanya digunakan dalam aplikasi web tradisional (non-RESTful), sehingga data terkait yang dibutuhkan untuk menjalankan operasi tertentu tidak disimpan di dalam path URL
- Data secara otomatis akan diencode oleh framework sebelum dimasukkan ke dalam query URL.

@PathVariable
- Digunakan untuk mengekstrak data dari URL template
- Biasanya digunakan dalam pengembangan RESTful web services, di mana data yang diperlukan untuk menjalankan suatu operasi disimpan di dalam URL.
- Data tidak secara otomatis diencode oleh framework. Misalnya terdapat karakter khusus pada data yang dikirimkan seperti tanda baca, tanda baca tersebut akan tetap ada sesuai bentuk aslinya di URL.


### What I did not understand
- [ ] Operasi-operasi penting pada Git
- [x] Masih belum terbiasa menggunakan Spring Boot

