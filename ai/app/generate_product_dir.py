import os

ROOT_DIR = 'products/test/'
dirList = []
# 1. 데이터셋에서 모든 데이터 가져오기
with open('dataset/split/validation.txt', 'r', encoding='utf') as file:
    lines = file.readlines()
    for item in lines:
        itemArr = item.strip().split('/')
        # 우리가 고려하지 않는 데이터들 제거
        if itemArr[2] == '홈클린' or itemArr[2] == '생활용품' or itemArr[2] == '의약외품' or itemArr[2] == '이_미용' or itemArr[2] == '주류':
            continue
        if itemArr[3] not in dirList:
            dirList.append(itemArr[3])

print(dirList)
print(len(dirList))

# 2. 데이터 디렉토리 생성
for p in dirList:
    if not os.path.exists(ROOT_DIR + p):
        os.mkdir(ROOT_DIR + p)