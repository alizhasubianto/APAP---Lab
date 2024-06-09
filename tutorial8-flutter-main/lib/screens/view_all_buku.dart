import 'dart:convert';
import 'package:bacabaca_mobile/model/BukuResponse.dart';
import 'package:bacabaca_mobile/utils/reusable_widget.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class BukuScreen extends StatefulWidget {
  static const routeName = '/appointment/list';
  const BukuScreen({Key? key}) : super(key: key);

  @override
  State<BukuScreen> createState() => _BukuScreenState();
}

class _BukuScreenState extends State<BukuScreen> {
  Future<List<Buku>?> fetchBuku() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? token = prefs.getString('token');
    try {
      var response = await http.get(
        Uri.parse("http://localhost:8080/api/buku/view-all"),
        headers: {
          "Authorization": "Bearer $token",
          "Content-Type": "application/json",
        },
      );

      if (response.statusCode == 200) {
        List<dynamic> jsonList = json.decode(response.body);
        List<Buku> books = jsonList.map((e) => Buku.fromJson(e)).toList();
        return books;
      } else {
        print("Failed to fetch books. Status code: ${response.statusCode}");
        return null;
      }
    } catch (e) {
      print("Error fetching books: $e");
      return null;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        automaticallyImplyLeading: false,
        backgroundColor: const Color(0XFF5d675b),
        actions: [
          Padding(
            padding: const EdgeInsets.only(right: 10),
            child: IconButton(
              onPressed: () {
                popUpExit(context, "Log out of your account?");
              },
              icon: const Icon(
                Icons.account_circle_outlined,
                size: 40,
                color: Colors.white,
              ),
            ),
          ),
        ],
      ),
      body: FutureBuilder<List<Buku>?>(
        future: fetchBuku(),
        builder: (BuildContext context, AsyncSnapshot<List<Buku>?> snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text("Error: ${snapshot.error}"));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text("Buku tidak ditemukan"));
          } else {
            List<Buku> books = snapshot.data!;
            return ListView.builder(
              itemCount: books.length,
              itemBuilder: (BuildContext context, int index) {
                Buku book = books[index];
                return Container(
                  margin:
                      const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
                  padding: const EdgeInsets.all(20.0),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(15.0),
                    boxShadow: const [
                      BoxShadow(
                        color: Colors.black,
                        blurRadius: 2.0,
                      )
                    ],
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        book.judul,
                        style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 8),
                      Text(
                        "Tahun Terbit: ${book.tahunTerbit}",
                        style: const TextStyle(fontSize: 16),
                      ),
                      const SizedBox(height: 8),
                      Text(
                        "Harga: ${book.harga}",
                        style: const TextStyle(fontSize: 16),
                      ),
                    ],
                  ),
                );
              },
            );
          }
        },
      ),
    );
  }
}
