package test4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class main {

	private static String Path;
	private static File file = new File("D:\\OS\\fat.txt");
	private static FileManager fileManager = new FileManager();

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Path = "D:\\OS";
		Scanner scanner = new Scanner(System.in);
		System.out.println("是否初始化？y/n");
		String string = scanner.nextLine();
		if (string.equals("y")) {
			deleteDir(Path);
			File dirFile = new File(Path);
			if (!dirFile.exists()) {
				if (!dirFile.mkdir())
					try {
						throw new Exception("目录不存在，创建失败！");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} else {
				System.out.println("该目录已存在！");
			}
			fileManager.init(file);
			menu();
		} else {
			menu();
		}
	}

	public static void menu() {
		System.out.println("dir     :列出目录内容");
		System.out.println("tree    :以树形结构显示所有目录内容");
		System.out.println("md      :创建目录,如md subdir");
		System.out.println("cd      :切换目录,返回上一级只能用cd \\返回到根目录");
		System.out.println("rd      :删除空目录,如rd subdir");
		System.out.println("mk      :创建文件,如:mk test或mk test 2000");
		System.out.println("del     :删除文件,如:del test");
		System.out.println("info    :查看FAT表");
		System.out.print(Path + "\\");
		Scanner in = new Scanner(System.in);
		String a = in.nextLine();
		String b1 = null, b2 = null;
		int size = 0;
		int c = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == 32) {
				c++;
			}
		}
		if (c == 0) {
			b1 = a;
		} else if (c == 1) {
			b1 = a.substring(0, a.indexOf(" "));
			b2 = a.substring(a.indexOf(" ") + 1);
		} else {
			b1 = a.substring(0, a.indexOf(" "));
			b2 = a.substring(a.indexOf(" ") + 1, a.lastIndexOf(" "));
			size = Integer.parseInt(a.substring(a.lastIndexOf(" ") + 1));
		}
		switch (b1) {
		case "dir":// 列出目录内容
			fileManager.print(new File(Path));
			menu();
			break;
		case "tree":// 以树形结构显示所有目录内容
			fileManager.printTree(new File("D:\\OS"));
			menu();
			break;
		case "md":// 创建目录,如md subdir
			fileManager.createFile(Path, b2);
			Path = Path + "\\" + b2;
			menu();
			break;
		case "cd":// 切换目录,返回上一级只能用cd \\返回到根目录
			String strParentDirectory = new File(Path).getParent();
			Path = strParentDirectory;
			menu();
			break;
		case "rd":// 删除空目录,如rd subdir
			fileManager.visitAll(new File(Path));
			fileManager.removeNullFile(fileManager.list);
			Path = "D:\\OS";
			menu();
			break;
		case "mk":// 创建文件,如:mk test或mk test 2000
			if (size == 0) {
				fileManager.createFile(new File(Path + "\\\\" + b2), 1024);
			} else {
				fileManager.createFile(new File(Path + "\\\\" + b2), size);
			}
			menu();
			break;
		case "del":// 删除文件,如:del test
			fileManager.delDirectory(new File(Path + "\\\\" + b2));
			menu();
			break;
		case "info":// 查看FAT表
			fileManager.showFAT();
			menu();
			break;
		default:
			break;
		}
	}

	public static void deleteDir(String dirPath) {
		File file = new File(dirPath);
		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			if (files == null) {
				file.delete();
			} else {
				for (int i = 0; i < files.length; i++) {
					deleteDir(files[i].getAbsolutePath());
				}
				file.delete();
			}
		}
	}

}
