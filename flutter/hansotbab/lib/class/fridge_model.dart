class FridgeModel {
  final int fridgeId;
  final String fridgeLocationName;
  final String fridgeLocationAddress;
  // final String donated;

  FridgeModel({
    required this.fridgeId,
    required this.fridgeLocationName,
    required this.fridgeLocationAddress,
    // required this.donated,
  });

  factory FridgeModel.fromJson(Map<String, dynamic> json) {
    return FridgeModel(
      fridgeId: json['fridgeId'],
      fridgeLocationName: json['fridgeLocationName'],
      fridgeLocationAddress: json['fridgeLocationAddress'],
      // donated: json['donated'],
    );
  }
}
