class Buku {
  Buku({
    required this.uuid,
    required this.judul,
    required this.harga,
    required this.penerbit,
    required this.tahunTerbit
  });

  String uuid;
  String judul;
  String? penerbit;
  double? harga;
  String? tahunTerbit;

  factory Buku.fromJson(Map<String, dynamic> json) => Buku(
    uuid: json["id"],
    judul: json["judul"],
    penerbit: json["penerbit"],
    tahunTerbit: json["tahunTerbit"],
    harga: json["harga"],
  );
}