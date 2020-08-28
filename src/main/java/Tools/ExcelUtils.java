package Tools;

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
import org.testng.annotations.Test;

public class ExcelUtils {

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
     * @param startRow
     *            开始的行数
     * @param endRow
     *            结束的行数
     * @param startCell
     *            开始的列
     * @param endCell
     *            结束的列
     */
    /* 该方法通过opi技术将 excel中的内容 循环读取到一个二维数组中 */
    public static Object[][] read(String path, int sheetNum, int startRow, int endRow, int startCell, int endCell) {
        Object[][] datas = new Object[endRow - startRow + 1][endCell - startCell + 1];
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(path)));
            Sheet sheet = workbook.getSheetAt(sheetNum - 1);
            for (int i = startRow; i <= endRow; i++) {
                Row row = sheet.getRow(i - 1);
                for (int j = startCell; j <= endCell; j++) {
                    Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);// 获取的格子中的内容如果为null
                    // 用""替换
                    // 设置每一列的数据类型为字符串
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();// 获取列的值
                    datas[i - startRow][j - startCell] = value;
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
        Object[][] obj = ExcelUtils.read("res/test_data.xlsx",1,2,6,1,5);
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

