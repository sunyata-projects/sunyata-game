package org.sunyata.game.majiang.core.service.yunfeng.table.src.org.yungege.mj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SetTable {
	private HashMap<Integer, Boolean> m_tbl = new HashMap<Integer, Boolean>();

	public boolean check(Integer number) {
		return m_tbl.containsKey(number);
	}

	public void add(int key) {
		if (m_tbl.containsKey(key))
			return;

		m_tbl.put(key, true);
	}

	public void dump(String name) {
	}
//    public List readJson(String name) throws IOException {
//        Resource resource = applicationContext.getResource(String.format("classpath:%s.json", name));
//
////        File file = resource.getFile();
////        byte[] buffer = new byte[(int) file.length()];
////        FileInputStream is = new FileInputStream(file);
////
////        is.read(buffer, 0, buffer.length);
////
////        is.close();
////        String str = new String(buffer);
//        InputStream inputStream = resource.getInputStream();
//        String str = convertStreamToStream(inputStream);
//        List List = Json.decodeValue(str, List.class);
//        //System.out.println(str);
//        return List;
//
//    }

	public void load(String path) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = "";
//			int num=0;
			while ((line = br.readLine()) != null) {
//				System.out.println("line:" + line);
				m_tbl.put(Integer.parseInt(line), true);
//				num++;
			}
			br.close();
//			System.out.printf("load %s: num=%d\n", path, num);
//			if(m_tbl.containsKey(200000000)){
//				System.out.println("contains 200000000");
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
