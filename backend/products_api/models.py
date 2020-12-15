from django.db import models

# Create your models here.

class Shelf(models.Model):
    # class modelling a database table containing information about the shelf
    id = models.IntegerField(primary_key=True, unique=True)
    x = models.FloatField(null=True)
    y = models.FloatField(null=True)

class Product(models.Model):
    # class modelling a database table containing information about the product
    # id = models.IntegerField(primary_key=True, unique=True)
    code = models.CharField(max_length=13)
    product_name = models.CharField(max_length=100)
    manufacturer = models.CharField(max_length=100)
    shelves = models.ManyToManyField(Shelf, through='ProductsOnShelves')

    # Create

    # Retrieve

    # Update

    # Delete

class ProductsOnShelves(models.Model):
    # class modelling a database table containing information about the products being stored on certain shelves and the amount of them in the location
    shelf = models.ForeignKey(Shelf, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    amount = models.IntegerField(default=0)
