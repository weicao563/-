package Tools;


import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class UtilsExcel {

    /**
     * 读取excel数据
     * 1.创建excel的Workbook对象
     * 2.获取sheet对象
     * 3.获取行
     * 4.获取列
     * 5.将获取的值分别放入二维数组，并返回
     * @param path
     *            文件路径
     * @param sheetNum
     * 			  excel的sheet编号

     */
    /* 该方法通过opi技术将 excel中的内容 循环读取到一个二维数组中 */
    public static Object[][] read(String path, int sheetNum) {
//		Object[][] datas = new Object[endRow - startRow + 1][endCell - startCell + 1];
        Object[][] datas = null;
        int rowCount = 0;
        int cellCount = 0;
        try {
            File file = new File(path);
            InputStream in = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheetAt(sheetNum - 1);
            int lastRowNum = sheet.getLastRowNum();
            int physicalNumberOfCells = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int i = 0; i < lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(0)!= null && row.getCell(0).toString().equals("") && rowCount == 0) {
                    rowCount = i-1;
                }
                for (int j = 0; j < physicalNumberOfCells; j++) {
                    if (row.getCell(j)!= null && row.getCell(j).toString().equals("") && cellCount == 0) {
                        cellCount = j ;
                    }
                }
            }
            datas = new Object[rowCount][cellCount];
            for (int i = 2; i <= rowCount+1; i++) {
                Row row = sheet.getRow(i - 1);
                for (int j = 1; j <= cellCount; j++) {
                    Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);// 获取的格子中的内容如果为null
                    // 用""替换
                    // 设置每一列的数据类型为字符串
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();// 获取列的值
                    datas[i - 2][j - 1] = value;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datas;
    }

    @Test
    public void testGetData() {
        Object[][] obj = UtilsExcel.read("C:/Users/weicao/Desktop/test_data.xlsx",1);
        for (Object[] objects : obj) {
            System.out.println(Arrays.toString(objects));
        }
    }

    /**
     * 往excel指定行的指定列写入数据
     *
     * @param sheetNum
     *            要操作的表单索引
     * @param caseId
     *            用例编号
     * @param cellNum
     *            列编号
     * @param result
     *            要写入的结果数据
     * @param filePath
     *            要写入的文件路径（非classpath路径）
     */

    public static void write(int sheetNum, String caseId, int cellNum, String result, String filePath) {
        try {
            InputStream is = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(sheetNum);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String value = cell.getStringCellValue();
                if (value.equals(caseId)) {
                    Cell cellToBeWirte = row.getCell(cellNum - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cellToBeWirte.setCellType(CellType.STRING);
                    cellToBeWirte.setCellValue(result);
                    break;
                }
            }
            OutputStream os = new FileOutputStream(new File(filePath));
            workbook.write(os);
            os.close();
            is.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
