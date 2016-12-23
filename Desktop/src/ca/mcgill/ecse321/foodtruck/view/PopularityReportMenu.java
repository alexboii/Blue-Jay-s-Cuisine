package ca.mcgill.ecse321.foodtruck.view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Map;


import javax.swing.JTable;

import ca.mcgill.ecse321.foodtruck.controllers.TransactionController;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;


@SuppressWarnings("serial")
public class PopularityReportMenu extends JFrame {
	private JTable table;
	public PopularityReportMenu() {
		
		Map<MenuItem,Integer> sorted = TransactionController.getTopTenMenuItemsCurrentWeek();
		Iterator<MenuItem> it =sorted.keySet().iterator();
		

		Object[][] data = new Object[10][4];
		String[] columnNames = new String[]{"Menu Item","Price","Sold","Sales"};
		
		int count =0;
		while(count<10){
			if(it.hasNext())
			{
				MenuItem next = it.next();
				data[count] = new Object[]{next.getName(),next.getPrice()+"$",sorted.get(next),((int)sorted.get(next))*((int)next.getPrice())+"$"};
			}
			else
			{
				data[count]= new Object[]{"","","",""	};
			}
			count++;
		}

		getContentPane().setLayout(new BorderLayout(0, 0));
		
		table = new JTable(data,columnNames);
		table.setFillsViewportHeight(true);

		getContentPane().add(table.getTableHeader(), BorderLayout.NORTH);
		getContentPane().add(table, BorderLayout.CENTER);
		this.setSize(new Dimension(500,500));

	}

}
