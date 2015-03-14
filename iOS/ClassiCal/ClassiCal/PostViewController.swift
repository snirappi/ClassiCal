//
//  PostViewController.swift
//  ClassiCal
//
//  Created by 曾 锦涛 on 3/6/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class PostViewController: UITableViewController {
    
    
    
    @IBAction func cancel(sender: AnyObject) {
        dismissViewControllerAnimated(true, completion: nil)
        
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}