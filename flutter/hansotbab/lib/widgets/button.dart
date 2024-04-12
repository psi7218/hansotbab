import 'package:flutter/material.dart';

class Button extends StatelessWidget {
  final String text;
  final Color bgColor;
  final Color textColor;
  final double? width;
  final double? height;
  final Widget? image;
  final MainAxisAlignment? alignment;
  final double borderRadius; // 기본값이 null이 아니도록 변경하거나 필수 매개변수로 만들기

  const Button({
    super.key,
    required this.text,
    required this.bgColor,
    required this.textColor,
    this.width,
    this.height,
    this.image,
    this.alignment = MainAxisAlignment.center,
    this.borderRadius = 20.0, // 기본 borderRadius 값을 제공
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      width: width,
      height: height,
      decoration: BoxDecoration(
        color: bgColor,
        borderRadius: BorderRadius.circular(borderRadius),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 50),
        child: Row(
          mainAxisAlignment: alignment ?? MainAxisAlignment.center,
          children: [
            if (image != null) ...[
              image!,
              const SizedBox(width: 10), // 이미지와 텍스트 사이 간격
            ],
            Text(
              text,
              style: TextStyle(
                color: textColor,
                fontSize: 20,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
