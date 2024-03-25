import 'package:flutter/material.dart';

class LoginState with ChangeNotifier {
  bool _isLoggedIn = false;

  bool get isLoggedIn => _isLoggedIn;

  void login() {
    _isLoggedIn = true;
    notifyListeners(); // 상태가 변경되었음을 알림
  }

  void logout() {
    _isLoggedIn = false;
    notifyListeners(); // 상태가 변경되었음을 알림
  }
}
