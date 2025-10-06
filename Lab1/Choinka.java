public class Choinka{
	public static void main(String[] args){

		if (args.length == 0){
			System.out.println("There must be a height!");
			return;
        }

		int height = Integer.valueOf(args[0]);
		String txt = "*";

		for (int i = 1; i <= height; i++){
			
			System.out.println(txt.repeat(i));
			}
	}
}