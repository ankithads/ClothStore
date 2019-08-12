README

Implemented stories:

	1. As a Customer I can view the products and their category, price and availability information. 
	
	2. As a Customer I can add a product to my shopping cart. 
	
	3. As a Customer I can remove a product from my shopping cart. 
	
	4. As a Customer I can view the total price for the products in my shopping cart.
	
	5. As a Customer I am unable to add Out of Stock products to the shopping cart. 

To-DO:

	1. As a Customer I can add a product to my wishlist.
	
	2. As a Customer I can remove a product from my wishlist. 
	
	3. As a Customer I can move a product from my wishlist to the shopping cart. 
	

ActivitFiles:


HomeActivity:

	- This contains Navigation to Catalogue, Cart, WishList
	
CatalogueActivity:

	- Sends the GET request to API to get the list of All the products
	
	- Displays the items on the screen
	
	- Sends the POST request to API to add the item into the shopping cart
	
CartActivity:

	- List the Item in the cart
	
	- Gets the Totalprice of the products from shopping cart
	
	- Sends DELETE request to delete the item in the cart
	
	
Note:
All the screens has Home button which on clicking goes to home page.
Back functionality does not work.
