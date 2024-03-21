import 'package:flutter/material.dart';

class Favorite extends StatelessWidget {
  const Favorite({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
        padding: const EdgeInsets.all(30),
        child: const Row(
          children: [
            Text(
              '즐겨찾기',
              style: TextStyle(fontSize: 20),
            ),
          ],
        ));
  }
}
