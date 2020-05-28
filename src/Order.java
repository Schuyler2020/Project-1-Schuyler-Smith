
import java.io.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.*;

public class Order {
    private int numofitems = 0;
    private double subtotal = 0;
    private double total = 0;
    private int totalItems = 0;
    private int maxNumItems = -1;
    private String filename = "transactions.txt";
    private ArrayList<String> items = new ArrayList<>(); //all confirmed items
    private StringBuilder viewOrder = new StringBuilder();
    private StringBuilder finalorder = new StringBuilder();

    File file = new File(filename);
    String[] itemInfo = new String[6];

    BigDecimal num  = new BigDecimal("58.987");


    public String getFinishOrder() {
        return this.finalorder.toString();
    }


    public void setFinishOrder(String date, String time) {
        this.setOrderTotal();
        this.finalorder.append("Date: " + date + " " + time);
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append("Number of line items: " + this.getTotalItems());
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append("Item# /ID / Price / Qty / Disc %/ Subtotal");
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append(this.getViewOrder());
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append("Order subtotal:   $" + new DecimalFormat("#0.00").format(this.getOrderTotal()));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append("Tax rate:     6%");
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append("Order total:      $" + new DecimalFormat("#0.00").format(this.getOrderSubtotal()));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append(System.getProperty("line.separator"));
        this.finalorder.append("Thanks for shopping at the Ye Olde Book Shoppe!");


    }

    public String getViewOrder() {
        return this.viewOrder.toString();
    }

    public void addtoOrder(String order) {
        viewOrder.append(this.getTotalItems() + ". " + order);
        viewOrder.append(System.getProperty("line.separator"));
    }

    public String[] getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String bookID, String title, String price, String quantityOfItem, String discountPercentage, String totalDiscount) {
        itemInfo[0] = bookID;
        itemInfo[1] = title;
        itemInfo[2] = price;
        itemInfo[3] = quantityOfItem;
        itemInfo[4] = discountPercentage;
        itemInfo[5] = totalDiscount;
    }

    public double getTotalDiscount(int quantity, double bookPrice) {

        if(quantity >= 1 && quantity <= 4 )
            return (quantity * bookPrice); //0% discount
        if(quantity >= 5 && quantity <= 9)
            return .10 * (quantity * bookPrice); //10% discount
        if(quantity >= 10 && quantity <= 14)
            return .15 * (quantity * bookPrice); //15% discount
        if(quantity >= 15)
            return .20 * (quantity * bookPrice); //20% discount

        return 0.0;
    }

    public int discount(int quantity) {
        if(quantity >= 1 && quantity <= 4 )
            return 0; //0% discount
        if(quantity >= 5 && quantity <= 9)
            return 10;//10% discount
        if(quantity >= 10 && quantity <= 14)
            return 15; //15% discount
        if(quantity >= 15)
            return 20; //20% discount
        return 0;
    }

    public String viewOrder() {
        return filename;

    }

    public void prepareTransaction() {
        String lineItem = new String();
        for(int i = 0; i< this.itemInfo.length; i++){
            lineItem += this.itemInfo[i] + ", ";
        }
        items.add(lineItem);
    }

    public void printTransactions() throws IOException {
        Calendar calendar= Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat permutation = new SimpleDateFormat("yyMMddyyHHmm");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a z");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

        //set finsihOrder string builder for dialog box
        this.setFinishOrder(dateFormat.format(date), time.format(date));

        //create file if none exist
        if(file.exists() == false) {
            file.createNewFile();
        }

        //append only if file exist
        PrintWriter outputStream = new PrintWriter(new FileWriter(filename, true));

        //write to file
        for(int i = 0; i< this.items.size(); i++){
            outputStream.append(permutation.format(date) + ", ");
            String lineItem = this.items.get(i);
            outputStream.append(lineItem);
            outputStream.append(dateFormat.format(date) + ", ");
            outputStream.append(time.format(date));
            outputStream.println();
        }

        outputStream.flush();
        outputStream.close();
    }


    public int getCurrentNumItems() {
        return numofitems;
    }
    public void setCurrentNumItems(int currentNumItems) {
        this.numofitems = this.numofitems+ currentNumItems;
    }
    public double getOrderSubtotal() {
        return subtotal;
    }
    public void setOrderSubtotal(int quantity, double bookPrice) {
        this.subtotal = this.subtotal + this.getTotalDiscount(quantity, bookPrice);
    }
    public int getTotalItems() {
        return totalItems;
    }
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    public int getMaxNumItems() {
        return maxNumItems;
    }
    public void setMaxNumItems(int maxNumItems) {
        this.maxNumItems = maxNumItems;
    }
    public double getOrderTotal() {
        return total;
    }

    public void setOrderTotal() {
        this.total = this.total + (.06 * this.total);
    }



}