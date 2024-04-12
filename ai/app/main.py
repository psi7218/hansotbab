import sys
sys.path.append("/code/app/src/LotteClassification")
print(sys.path)
import LotteClassification
import os
import shutil
import json
from fastapi import FastAPI, File, UploadFile
import uuid
import torch
print(torch.cuda.is_available())

ROOT_DIR = 'products/test/'
CHECKPOINT_PATH = 'models/ours/checkpoint/best_top1_validation.pth'
DATA_ROOT = 'products'
CONFIG_PATH = 'configs/efficientnet_b0_ours.yaml'
SAVE_ROOT = '/content/save_root'
PRODUCT_JSON_PATH = 'product_meta_info.json'
IMAGE_PATH = ''

app = FastAPI()

def main():
    print("main")
    print(IMAGE_PATH)
    # generate_data_folder()
    copy_inputImage_to_folder(IMAGE_PATH)
    item = run()
    delete_inputImage()
    print(item)
    return item

@app.post("/upload-image")
async def upload_photo(file: UploadFile):
    global IMAGE_PATH
    UPLOAD_DIR = "images"  # 이미지를 저장할 서버 경로

    content = await file.read()
    # print(content)
    filename = f"{str(uuid.uuid4())}.jpg"  # uuid로 유니크한 파일명으로 변경
    with open(os.path.join(UPLOAD_DIR, filename), "wb") as fp:
        fp.write(content)  # 서버 로컬 스토리지에 이미지 저장 (쓰기)

    image_path = UPLOAD_DIR + "/" + filename
    IMAGE_PATH = image_path
    item = main()
    return {"productName": item[0], "productCategory": item[1]}

def run():
    # 모델 run
    lotte_classification = LotteClassification.LotteClassification()
    lotte_classification.init_from_cfg(cfg_path=CONFIG_PATH, device='cuda')
    lotte_classification.load_checkpoint(checkpoint_path=CHECKPOINT_PATH, device='cuda')
    item_cd = lotte_classification.inference(data_root=DATA_ROOT)
    # item_cd = "14960100001"
    return find_item(item_cd)



def generate_data_folder():
    dirList = []
    # 1. 데이터셋에서 모든 데이터 가져오기
    with open('dataset/split/validation.txt', 'r', encoding='utf') as file:
        lines = file.readlines()
        for item in lines:
            itemArr = item.strip().split('/')
            # 우리가 고려하지 않는 데이터들 제거
            if itemArr[2] == '홈클린' or itemArr[2] == '생활용품' or itemArr[2] == '의약외품' or itemArr[2] == '이_미용' or itemArr[
                2] == '주류':
                continue
            if itemArr[3] not in dirList:
                dirList.append(itemArr[3])

    print(dirList)
    print(len(dirList))

    # 2. 데이터 디렉토리 생성
    for p in dirList:
        if not os.path.exists(ROOT_DIR + p):
            os.mkdir(ROOT_DIR + p)

def copy_inputImage_to_folder(inputImage_path):
    # 입력받은 이미지 파일 위치
    # inputImage_path = 'bluepocky.jpg'
    # 복사할 폴더
    to_path = ''

    # 전체 데이터 폴더
    folder_list = [name for name in os.listdir(ROOT_DIR) if os.path.isdir(os.path.join(ROOT_DIR, name))]
    print(folder_list)
    # 파일을 모든 폴더에 복사
    for f in folder_list:
        to_path = ROOT_DIR + f
        # print(to_path)
        shutil.copy(inputImage_path, to_path)

def delete_inputImage():
    # 전체 데이터 폴더
    folder_list = [name for name in os.listdir(ROOT_DIR) if os.path.isdir(os.path.join(ROOT_DIR, name))]
    # print(folder_list)
    del_path = ''
    for f in folder_list:
        del_path = ROOT_DIR + f
        if os.path.exists(del_path):
            for file in os.listdir(del_path):
                os.remove(del_path + '/' + file)

    # input 이미지 삭제
    os.remove(IMAGE_PATH)


def find_item(item_cd):
    with open(PRODUCT_JSON_PATH, 'r', encoding='utf-8') as file:
        # print(file)
        datas = json.loads(file.read())
        # print(datas[item_cd])
        try:
            item_info = datas[item_cd]
            if item_info is not None:
                return item_info
        except Exception as e:
            print(e)
            return ["Product no:wqt found", "None"]


if __name__=='__main__':
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port = 8000)
    # main()
