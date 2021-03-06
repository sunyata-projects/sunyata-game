package org.sunyata.game.majiang.core.service.yunfeng.table.src.org.yungege.test;


import junit.framework.TestCase;
import org.sunyata.game.majiang.core.service.yunfeng.table.src.gen_table.TableMgr;
import org.sunyata.game.majiang.core.service.yunfeng.table.src.org.yungege.mj.Hulib;
import org.sunyata.game.majiang.core.service.yunfeng.table.src.org.yungege.mj.Program;

public class Test extends TestCase
{
	public Test()
	{
		TableMgr.getInstance().load();
	}
	
	@org.junit.Test
	public void test()
	{
		
	}

	public void testOne()
	{
		int guiIndex = 33;
		int[] cards = { 
			0, 0, 0, 1, 1, 1, 0, 0, 0, /* 万 */ 
			1, 1, 1, 0, 0, 0, 0, 0, 0, /* 筒 */
			2, 0, 0, 0, 0, 0, 0, 0, 0, /* 条 */
			0, 0, 0, 0, 0, 0, 0 };//字

		System.out.println("测试1种,癞子:" + guiIndex);
		Program.print_cards(cards);
		assertEquals(Hulib.getInstance().get_hu_info(cards, 34, guiIndex),true);
		
		guiIndex = 34;
		cards = new int[]{ 
			0, 0, 0, 1, 1, 1, 0, 0, 0, /* 万 */ 
			1, 1, 1, 0, 0, 0, 0, 0, 0, /* 筒 */
			3, 0, 0, 0, 0, 3, 0, 0, 0, /* 条 */
			2, 0, 0, 0, 0, 0, 0 };//字

		System.out.println("测试1种,癞子:" + guiIndex);
		Program.print_cards(cards);
		assertEquals(Hulib.getInstance().get_hu_info(cards, 34, guiIndex),true);
		
		guiIndex = 18;
		cards = new int[]{ 
			1, 0, 1, 0, 1, 0, 1, 0, 0, /* 万 */ 
			1, 1, 1, 0, 0, 0, 3, 0, 0, /* 筒 */
			2, 0, 0, 0, 0, 0, 0, 0, 0, /* 条 */
			2, 0, 0, 0, 0, 0, 0 };//字

		System.out.println("测试1种,癞子:" + guiIndex);
		Program.print_cards(cards);
		assertEquals(Hulib.getInstance().get_hu_info(cards, 34, guiIndex),true);
	}
}
