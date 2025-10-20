public class Choinka{
	public static void main(String[] args){

		if (args.length == 0){
			System.out.println("There must be a height!");
			return;
        }

		int height = Integer.valueOf(args[0]);
		String star = "*";
		String trunk = "|||";
		String space = " ";

		//printing stars
		for (int i = 1; i <= height; i++){
			
			System.out.println((space.repeat(height-i)) + star.repeat(2*i-1));
			}
		
		// printing a trunk
		if (height > 3) {
			for (int j = 1; j <= 2; j++) {
				System.out.println(space.repeat(height - 2) + trunk);
			}
		} else if (height == 3) {
			System.out.println(space.repeat(height - 2) + trunk);
		}

	}
}