// BY Hiki

#include<stdio.h>
#include<string.h>
//#include<stdlib.h>
#define ENTRY_SIZE 0x20
#define ROOTDIR_OFFSET 0x2600
#define FAT_OFFSET 0x200
#define DATA_OFFSET 0x4200
#define CLUS_SIZE 0x200
#define TRUE 1
#define FALSE 0

const char* path = "a.img";

// 根目录区信息
struct Entry
{
    char fileName[11];  // 文件名
    char attr;  // 文件属性，没用
    char reversed[14]; // 保留属性
    char fstClus[2];  // 此条目对应的开始簇号，重要
    char fileSize[4];  // 文件大小
};

struct FileInfo
{
    char path[120];
    char fileName[12];
    int offset;
} fileInfo[50];

struct DirInfo
{
    char path[120];
    char fileName[12];
    int offset;
} dirInfo[50];

FILE* file;
struct Entry entry;
struct Entry* entryPtr = &entry;
int filePointer = 0;
int dirPointer = 0;

extern void myPrint(char* str, int length);

int getFATValue(int index);
void readFiles(int offset, char* path, int isRoot);
int isDir(struct Entry* entryPtr);
int isEntryEmpty(struct Entry* entryPtr);
int isFileNameValid(struct Entry* entryPtr);
void formatFileName(char fileName[], char* rawFileName);
int getClus(char clus[2]);
long getOffsetByClus(int clus);
void printCount(char* input);
void printContent(char* input);
void printDirContent(struct DirInfo dInfo);
void printFileContent(struct FileInfo fInfo);
void printCount(char* input);
void printCountInDir(long offset, char* name, int deep);
void getCommand();
void itoa(char* buffer, int num);
void toUpper(char* input);

int main(){

    // 以二进制读入文件
    file = fopen(path, "rb");
    if(file == NULL){
        char* prompt = "Open a.img failed!\n";
        myPrint(prompt, strlen(prompt));

    }

    // 将所有文件读入并打印出来，分别保存在fileInfo, dirInfo中
    readFiles(ROOTDIR_OFFSET, "", 1);

    // 等待输入命令
    getCommand();

    fclose(file);

}

void readFiles(int offset, char* path, int isRoot){
    // 保存当前的文件流指针
    long originStreamPtr = ftell(file);
    // 定位到根文件夹开始的位置
    fseek(file, offset, SEEK_SET);
   	int emptyFlag = 1;
    // 递归读取文件
    while(1) {
        // 每32位代表一个Entry，读取并显示出来
        fread(entryPtr, ENTRY_SIZE, 1, file);
        // 若读到空的Entry，停止读取
        if (isEntryEmpty(entryPtr)){
        	// 如果是空文件夹，输出文件夹名称
        	if (emptyFlag){
        		char* blue = "\033[34;1m";
                char* white = "\033[37;0m";
                myPrint(blue, strlen(blue));
                myPrint(path, strlen(path));
                myPrint(white, strlen(white));
                myPrint("\n", 2);
        	}
            break;
        }
        // 检验是否为合格的文件名
        if (isFileNameValid(entryPtr)) {
			emptyFlag = 0;
            // 将文件名提取出来
            char fFileName[12];
            formatFileName(fFileName, entryPtr->fileName);

            // 若读取到文件夹
            if (isDir(entryPtr)) {
                // 将path连接起来，构建新的path
                char tempPath[120] = "";
                strcpy(tempPath, path);
                strcat(tempPath, fFileName);
                strcat(tempPath, "/");

                // 构建新的文件夹，仅当在读取全部文件模式时
                if(isRoot){
                    strcpy(dirInfo[dirPointer].path, tempPath);
                    strcpy(dirInfo[dirPointer].fileName, fFileName);
                    dirInfo[dirPointer].offset = ftell(file) - ENTRY_SIZE;
                    dirPointer++;
                }

                // 遍历下一个文件夹
                int offset = getOffsetByClus(getClus(entryPtr->fstClus));
                readFiles(offset, tempPath, isRoot);

            }
            else {
                // 构建新的文件，仅当在读取全部文件模式时
                if (isRoot == 1){
                    strcpy(fileInfo[filePointer].fileName, fFileName);
                    strcpy(fileInfo[filePointer].path, path);
                    fileInfo[filePointer].offset = ftell(file) - ENTRY_SIZE;
                    filePointer++;
                }

                char* blue = "\033[34;1m";
                char* white = "\033[37;0m";

                myPrint(blue, strlen(blue));
                myPrint(path, strlen(path));
                myPrint(white, strlen(white));
                myPrint(fFileName, strlen(fFileName));
                myPrint("\n", 2);

//                printf("[34;1m%s", path);
//                printf("[37;0m%s\n", fFileName);

            }

        }
    }
    fseek(file, originStreamPtr, SEEK_SET);
    return;
}

void formatFileName(char fileName[], char* rawFileName){
    int index = 0;
    int lastIndex;
    // 定位文件名最后一个非空格字符
    for (int i = 7; i >= 0; i--){
        if(rawFileName[i] != ' '){
            lastIndex = i;
            break;
        }
    }

    for (int i = 0; i <= lastIndex; ++i){
        fileName[index] = rawFileName[i];
        index++;
    }

    if (rawFileName[8] != ' '){
        fileName[index] = '.';
        index++;
        for (int i = 8; i < 11; ++i){
            if (rawFileName[i] != ' '){
                fileName[index] = rawFileName[i];
                index++;
            } else {
                break;
            }
        }
    }

    fileName[index] = '\0';

}

int isEntryEmpty(struct Entry* entryPtr){
    int i = 0;
    for (i = 0; i < ENTRY_SIZE; i++){
        if (((char*)entryPtr)[i] != 0){
            return FALSE;
        }
    }
    return TRUE;
}

int isFileNameValid(struct Entry* entryPtr){
    // char fileName[11] = entryPtr->fileName;
    int i;
    for (i = 0; i < 11; ++i){
        char b = entryPtr->fileName[i];
        if (!(b == 32 || (b >= 48 && b <= 57) || (b >= 65 && b <= 90) || (b >= 97 && b <= 122))){
            return FALSE;
        }
    }

    return TRUE;
}

int isDir(struct Entry* entryPtr){
    if (entryPtr->attr == 0x10)
        return TRUE;
    return FALSE;
}

int getClus(char clus[2]){
    return (unsigned char)clus[1] * 256 + (unsigned char)clus[0];
}

int getFATValue(int index){
    // 保存当前的文件流指针
    long originStreamPtr = ftell(file);
    // 定位到相应的文件位置
    int fatIndex = FAT_OFFSET + index * 3/2;
    fseek(file, fatIndex, SEEK_SET);
    // 获取FAT值
    short fatValue = 0;
    fread(&fatValue, 2, 1, file);

    // 如果index为奇数，则前面半段为无效值；若为偶数，则为后面半段
    if (fatValue % 2){
        fatValue = (unsigned short)(fatValue & 0x0FFF);
    }
    else{
        fatValue = fatValue >> 4;
    }
    fseek(file, originStreamPtr, SEEK_SET);
    return fatValue;

}

long getOffsetByClus(int clus){
    return DATA_OFFSET + (clus-2) * CLUS_SIZE;

}

/*------------------------------------------------打印函数------------------------------------------------------*/


void getCommand(){

    char* prompt = "Enter your command(q to quit): \n";
	char* subPrompt = "->";
	char* blue = "\033[34;1m";
    char* white = "\033[37;0m";
    myPrint(prompt, strlen(prompt));

    while(TRUE){
		myPrint(blue, strlen(blue));
		myPrint(subPrompt, strlen(subPrompt));
		myPrint(white, strlen(white));
        char input[50];
        scanf("%s", input);
        // 如果是退出命令
        if(strcmp(input, "q") == 0){
            break;
        }
        // 如果是count命令
        if (!strcmp(input, "count")) {
            char input2[50];
            scanf("%s", input2);
            toUpper(input2);
            printCount(input2);
        }
            // 如果是第二个命令
        else{
            toUpper(input);
            printContent(input);
        }

    }

}

void printContent(char* input){
    // 判断输入的内容是文件还是文件夹
    char lastByte;
    for (int i = 0; i < strlen(input); ++i) {
        if(input[i] != '\0'){
            lastByte = input[i];
        }else{
            break;
        }
    }
    // 输入的是文件夹
    if(lastByte == '/'){
        // 判断输入的路径是否合法
        int pointer = -1;
        for (int i = 0; i < dirPointer; i++){
            if (strcmp(input, dirInfo[i].path) == 0){
                pointer = i;
                break;
            }
        }

        if (pointer != -1){
            printDirContent(dirInfo[pointer]);
        } else{
            char* prompt ="unknown path\n";
            myPrint(prompt, strlen(prompt));
        }
    }

        // 输入的是文件名
    else{
        int pointer = -1;
        for (int i = 0; i < filePointer; i++) {
            char path[120] = "";
            strcat(path, fileInfo[i].path);
            strcat(path, fileInfo[i].fileName);

            if (!strcmp(input, path)){
                pointer = i;
                break;
            }
        }

        if (pointer != -1){
            printFileContent(fileInfo[pointer]);
        } else {
            char* prompt ="unknown path\n";
            myPrint(prompt, strlen(prompt));
        }
    }

}

void printDirContent(struct DirInfo dInfo){
    // 保存当前的文件流指针
    long originStreamPtr = ftell(file);

    // 找到文件夹内容的位置并读取
    fseek(file, dInfo.offset, SEEK_SET);
    fread(entryPtr, ENTRY_SIZE, 1, file);
    long offset = getOffsetByClus(getClus(entryPtr->fstClus));
    readFiles(offset, dInfo.path, 0);

    fseek(file, originStreamPtr, SEEK_SET);
}

void printFileContent(struct FileInfo fInfo){
    // 先试试小于512字节的文件 TODO

    // 保存当前的文件流指针
    long originStreamPtr = ftell(file);

    // 找到文件内容的位置并读取文件信息
    fseek(file, fInfo.offset, SEEK_SET);
    fread(entryPtr, ENTRY_SIZE, 1, file);
    int index = getClus(entryPtr->fstClus);

    while(index != 0x00 && index < 0xFF0){
        // 读完一次
        long offset = getOffsetByClus(index);
        fseek(file, offset, SEEK_SET);
        char content[2];
        int count = 0;
        fread(content, sizeof(char), 1, file);
        while(content[0] != 0 && count < CLUS_SIZE){
            myPrint(content, 1); // TODO
//            printf("%c", content);
            fread(content, sizeof(char), 1, file);
            count++;
        }

        index = getFATValue(index);
    }

    fseek(file, originStreamPtr, SEEK_SET);

}

void printCount(char* input){

    // 在input后面我加上斜杠
    for (int i = 0; i <= strlen(input); ++i) {
        if (input[i] == '\0'){
            input[i] = '/';
            input[i+1] = '\0';
            break;
        }
    }

//    printf("input: %s\n", input);

    int pointer = -1;
    // 遍历文件夹
    for (int i = 0; i < dirPointer; ++i) {
        if(strcmp(input, dirInfo[i].path) == 0){
            pointer = i;
            break;
        }
    }

    if (pointer == -1){
        char* prompt = "Not a directory!\n";
        myPrint(prompt, strlen(prompt));
    } else {
        // 保存当前的文件流指针
        long originStreamPtr = ftell(file);
        fseek(file, dirInfo[pointer].offset, SEEK_SET);
        fread(entryPtr, ENTRY_SIZE, 1, file);
        long offset = getOffsetByClus(getClus(entryPtr->fstClus));
        fseek(file, originStreamPtr, SEEK_SET);
        printCountInDir(offset, dirInfo[pointer].fileName, 0);
    }

}

void printCountInDir(long offset, char* name, int deep){

    // 保存当前的文件流指针
    long originStreamPtr = ftell(file);
    // 定位到文件夹开始的位置
    fseek(file, offset, SEEK_SET);
    // 初始化
    int fileNum = 0;
    int dirNum = 0;
    // 先读取所有文件
    while(1) {

        // 每32位代表一个Entry，读取并显示出来
        fread(entryPtr, ENTRY_SIZE, 1, file);
        // 若读到空的Entry，停止读取
        if (isEntryEmpty(entryPtr))
            break;

        // 检验是否为合格的文件名
        if (isFileNameValid(entryPtr)) {
            if (isDir(entryPtr))
                dirNum++;
            else
                fileNum++;
        }
    }

    // 打印信息
    for (int i = 0; i < deep; ++i) {
//        printf("\t");
        myPrint("\t", 2);
    }

    char fileNumStr[10];
    char dirNumStr[10];
    itoa(fileNumStr, fileNum);
    itoa(dirNumStr, dirNum);

    char output[120] = "";
    strcat(output, name);
    strcat(output, ": ");
    strcat(output, fileNumStr);
    strcat(output, " files, ");
    strcat(output, dirNumStr);
    strcat(output, " directories\n");
    myPrint(output, strlen(output));

//    printf("%s: %d files, %d directories\n", name, fileNum, dirNum);

    // 读取里面的文件夹
    // 继续定位到文件夹开始的位置
    fseek(file, offset, SEEK_SET);
    while(1){
        // 每32位代表一个Entry，读取并显示出来
        fread(entryPtr, ENTRY_SIZE, 1, file);
        // 若读到空的Entry，停止读取
        if (isEntryEmpty(entryPtr))
            break;

        // 检验是否为合格的文件名
        if (isFileNameValid(entryPtr)) {
            if (isDir(entryPtr)){
                // 将文件名提取出来
                char dirName[12];
                formatFileName(dirName, entryPtr->fileName);
                long offset = getOffsetByClus(getClus(entryPtr->fstClus));
                printCountInDir(offset, dirName, deep+1);
            }

        }

    }


    fseek(file, originStreamPtr, SEEK_SET);
    return;
}


void itoa(char* buffer, int num){
    if(num == 0){
        buffer[0] = '0';
        buffer[1] = '\0';
        return;
    }

    // 先计算有多少位
    int count = 0;
    int temp = num;

    while(temp > 0){
        temp /= 10;
        count++;
    }
    temp = num;
    // 转化成字符串
    buffer[count] = '\0';
    for (int i = 0; i < count; ++i) {
        int bit = temp % 10;
        temp /= 10;
        buffer[count-1-i] = (char)(bit + 48);
    }
}

void toUpper(char* input){
    for (int i = 0; i < strlen(input); ++i) {
        if(input[i] >= 'a' && input[i] <= 'z'){
            input[i] += 'A' - 'a';
        }
    }
}

