//
//  AddPost.swift
//  ClassiCal
//
//  Created by 曾 锦涛 on 3/5/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class AddPost: UIViewController {
    
    @IBOutlet weak var titleText: UITextField!
    @IBOutlet weak var contentText: UITextField!
    
    var newItem: Post?
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        println("ssssssssss")
        if segue.identifier == "DoneItem" {
            let title = titleText.text
            let content = contentText.text
            if (title != nil && content != nil) {
                if (!title.isEmpty && !content.isEmpty) {
                    newItem = Post(title: title, content: content)
                }
            }
        }
    }
    
    /*func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return false
    }*/
    
    
    @IBAction func cancel(sender: AnyObject) {
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    override func viewDidLoad() {
        println("1 step")
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    


    
    
}