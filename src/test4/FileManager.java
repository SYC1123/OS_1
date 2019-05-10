package test4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileManager {
	List<File> list = new ArrayList<File>();
	public static File file = new File("D:\\OS\\fat.txt");
	static int[][] a = new int[8][8];

	public FileManager() {
		// TODO Auto-generated constructor stub
	}

	public void showFAT() {	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			int row=0;
			while((line=reader.readLine())!=null) {
				String[] temp=line.split("\t");
				for(int i=0;i<temp.length;i++) {
					a[row][i]=Integer.parseInt(temp[i]);
				}
				row++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 8; i++) {
			System.out.print("********");
			for (int j = 0; j < 8; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println("********");
		}

	}

	public static void init(File file) {
		try {
			FileWriter fileWriter = new FileWriter(file);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					fileWriter.write(a[i][j] + "\t");
				}
				fileWriter.write("\r\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public void createFile(String oldPath, String newPath) {
		File dirFile = new File(oldPath + "\\" + newPath);
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
	}

	// 删除指定目录下的所有空文件夹
	public void visitAll(File root) {
		File[] dirs = root.listFiles();
		if (dirs != null) {
			for (int i = 0; i < dirs.length; i++) {
				if (dirs[i].isDirectory()) {
					// System.out.println("name:" + dirs[i].getPath());
					this.list.add(dirs[i]);
				}
				visitAll(dirs[i]);
			}
		}
	}

	// 删除指定目录下的所有空文件夹
	public void removeNullFile(List<File> list) {
		for (int i = 0; i < list.size(); i++) {
			File temp = list.get(i);
			// 是目录且为空
			if (temp.isDirectory() && temp.listFiles().length <= 0) {
				temp.delete();
			}
		}
	}

	public void print(File f) {
		File[] fileArray = f.listFiles();
		for (int i = 0; i < fileArray.length; i++) {
			System.out.print(fileArray[i] + "\t");
			if (fileArray[i].isFile()) {
				if (!(fileArray[i].toString().equals("D:\\OS\\fat.txt"))) {
					try {
						BufferedReader reader = new BufferedReader(new FileReader(fileArray[i]));
						String line;
						line = reader.readLine();
						while ((line = reader.readLine()) != null) {
							System.out.print(line + "\t");
						}
						reader.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println();
		}

	}

	public void printTree(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				// listFiles()方法是返回某个目录下所有文件和目录的绝对路径，返回的是File数组
				File[] fileArray = f.listFiles();
				// System.out.println(fileArray.length);
				if (fileArray.length == 0) {
					System.out.println(f);
				}
				if (fileArray != null) {
					for (int i = 0; i < fileArray.length; i++) {
						printTree(fileArray[i]);
					}
				}
			} else {
				System.out.println(f);
			}
		}
	}

	public int createFile(File file, int size) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			int b, begin = 0;
			if (size % 1024 == 0) {
				b = (size / 1024);
			} else {
				b = (size / 1024) + 1;
			}
			int c = 0;
			int bi = -1, bj = -1;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (a[i][j] == 0) {
						if (bi == -1 || bj == -1) {
							bi = i;
							bj = j;
							begin = i * 10 + j;
							c++;
						} else {
							a[bi][bj] = (i + 1) * 10 + j + 1;
							bi = i;
							bj = j;
							c++;
						}
						if (c == b) {
							a[i][j] = -1;
							FileWriter fileWriter = new FileWriter(file);
							fileWriter.write(begin + "");
							fileWriter.write("\r\n");
							fileWriter.write(size + "");
							fileWriter.write("\r\n");
							fileWriter.write(df.format(new Date()));
							fileWriter.close();
							init(new File("D:\\OS\\fat.txt"));
							return 1;
						}
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public void delDirectory(File path) {
		if (path.isDirectory()) {
			path.delete();
		} else {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String line;
				line = reader.readLine();
				int begin = Integer.parseInt(line);
				reader.close();
				if (path.delete()) {
					// fat表删除
					int i = begin / 10;
					int j = begin % 10;
					begin = a[i][j];
					a[i][j] = 0;
					while (begin != -1) {
						i = (begin / 10) - 1;
						j = (begin % 10) - 1;
						begin = a[i][j];
						a[i][j] = 0;
					}

					init(file);

				} else {
					System.out.println("文件删除失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
